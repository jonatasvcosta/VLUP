from . import available_languages
from .model import Dictionary, TfIdfModel
from .preprocessing import PreProcessor

class KeywordsExtractor(object):

    language = None
    dictionary = None
    tfidf = None

    def __init__(self, language):
        if language not in available_languages:
            raise Exception("Language '{}' is not supported".format(language))

        self.language = language
        self.dictionary = Dictionary.load(language)
        self.tfidf = TfIdfModel.load(language)

    def keywords(self, text, limit=15):
        keywords_id_ranked = self._keywords_ranked(text, limit)

        return [ self.dictionary[item[0]] for item in keywords_id_ranked ]

    def keywords_with_score(self, text, limit=15):
        keywords_id_ranked = self._keywords_ranked(text, limit)

        return [ {"keyword": self.dictionary[item[0]], "score": item[1]} for item in keywords_id_ranked ]

    def _keywords_ranked(self, text, limit=15):
        bow = self.dictionary.doc2bow(PreProcessor.run(text))
        bow_tfidf = self.tfidf[bow]

        keywords_ranked = sorted(bow_tfidf, key=lambda x: x[1], reverse=True)

        if limit > 0:
            return keywords_ranked[:limit]
        else:
            return keywords_ranked
