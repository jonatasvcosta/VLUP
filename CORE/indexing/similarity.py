import gensim
from . import PathUtility
from .corpus import PreProcess
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
                self.similarity = gensim.similarity.docsim.Similarity.load(self.path)
            except:
                self.build_similarity()

    def build(self):
            self.build_tfidf()
            self.build_lsi()
            self.build_similarity()

    def build_tfidf(self):
        self.tfidf = TfIdfModel(self.corpus)

    def build_lsi(self):
        self.lsi = LsiModel(self.tfidf,
                            id2word=self.corpus.dictionary,
                            num_topics=self.dimensions)

    def build_similarity(self):
        self.similarity = gensim.similarity.docsim.Similarity(self.path,
                                                              corpus=self.lsi,
                                                              num_features=self.dimensions)

    def save(self):
        self.tfidf.save(self.language)
        self.lsi.save(self.language)
        self.similarity.save(self.path)

    def query(self, text, limit=15):
        document = self.corpus.dictionary.doc2bow(PreProcess.run(text))
        if len(document) == 0:
            raise Exception("Invalid query")

        document_tfidf = self.tfidf[document]
        document_lsi = self.lsi[document_tfidf]

        self.similarity.num_best = limit
        return self.similarity[document_lsi]


