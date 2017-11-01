import gensim
from . import PathUtility


class Dictionary(gensim.corpora.Dictionary):
    @classmethod
    def load(cls, language):
        return super(Dictionary, cls).load(PathUtility.DICTIONARY(language))

    def save(self, language):
        super().save(PathUtility.DICTIONARY(language))


class LsiModel(gensim.models.LsiModel):
    @classmethod
    def load(cls, language):
        return super(LsiModel, cls).load(PathUtility.LSI(language))

    def save(self, language):
        super().save(PathUtility.LSI(language))


class TfIdfModel(gensim.models.TfidfModel):
    @classmethod
    def load(cls, language):
        return super(TfIdfModel, cls).load(PathUtility.TF_IDF(language))

    def save(self, language):
        super().save(PathUtility.TF_IDF(language))
