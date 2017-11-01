import os


class PathUtility(object):
    ROOT = "/app"
    DATA = os.path.join(ROOT, "data")

    @classmethod
    def STOP_WORDS(cls, language):
        return os.path.join(cls.ROOT, "indexing/stopwords/{}.txt".format(language))

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


available_languages = os.listdir(PathUtility.DATA)
