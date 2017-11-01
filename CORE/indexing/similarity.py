from gensim.similarities.docsim import Similarity
from . import PathUtility
from .preprocessing import PreProcessor
from .model import LsiModel, TfIdfModel

class SimilarityLSA(object):
    corpus = None
    language = None
    path = None

    # num_topics / k: lsi space
    dimensions = None

    # models
    tfidf = None
    lsi = None
    similarity = None

    def __init__(self, corpus, language, dimensions=300):
        self.corpus = corpus
        self.language = language
        self.dimensions = dimensions
        self.path = PathUtility.SIMILARITY(language)

        # TF-IDF
        try:
            self.tfidf = TfIdfModel.load(language)
        except:
            self.build()

        # LSI Space
        if self.lsi is None:
            try:
                self.lsi = LsiModel.load(language)
            except:
                self.build_lsi()
                self.build_similarity()

        # Final similarity
        if self.similarity is None:
            try:
                self.similarity = Similarity.load(self.path)
            except:
                self.build_similarity()

    def build(self):
            self.build_tfidf()
            self.build_lsi()
            self.build_similarity()

    def build_tfidf(self):
        self.tfidf = TfIdfModel(self.corpus)

    def build_lsi(self):
        self.lsi = LsiModel(self.tfidf[self.corpus],
                            id2word=self.corpus.dictionary,
                            num_topics=self.dimensions)

    def build_similarity(self):
        self.similarity = Similarity(self.path,
                                     corpus=self.lsi[self.corpus],
                                     num_features=self.dimensions)

    def save(self):
        self.tfidf.save(self.language)
        self.lsi.save(self.language)
        self.similarity.save(self.path)

    def query(self, text, limit=15):
        document = self.corpus.dictionary.doc2bow(PreProcessor.run(text))
        if len(document) == 0:
            raise Exception("Invalid query")

        document_tfidf = self.tfidf[document]
        document_lsi = self.lsi[document_tfidf]

        self.similarity.num_best = limit
        return self.similarity[document_lsi]


