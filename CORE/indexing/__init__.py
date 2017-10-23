import os


class PathUtility(object):
    DATA = "/app/data"

    @classmethod
    def STOP_WORDS(cls, language):
        return os.path.join(cls.DATA, language, "stopwords")

    @classmethod
    def CORPUS_INDEX(cls, language):
        return os.path.join(cls.DATA, language, "corpus.index")

    @classmethod
    def DICTIONARY(cls, language):
        return os.path.join(cls.DATA, language, "model.dict")

    @classmethod
    def LSI(cls, language):
        return os.path.join(cls.DATA, language, "model.lsi")

    @classmethod
    def TF_IDF(cls, language):
        return os.path.join(cls.DATA, language, "model.tfidf")

    @classmethod
    def SIMILARITY(cls, language):
        return os.path.join(cls.DATA, language, "similarity/index")
