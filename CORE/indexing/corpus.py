import gensim
from newscraper.model import Article, Website
from .stopwords import StopWords
from .preprocessing import PreProcessor
from .model import Dictionary
from . import PathUtility


class ArticleIterable(object):
    index = None
    session = None
    chunksize = None

    def __init__(self, index, session, chunksize=1000):
        self.index = index
        self.session = session
        self.chunksize = chunksize

    def __iter__(self):
        for chunk_ids in gensim.utils.chunkize(self.index, self.chunksize):
            for result in self.session.query(Article.text).filter(Article.id.in_(chunk_ids)):
                yield result[0]


class DbCorpus(object):
    language = None
    session = None
    iterable = None
    index = None
    dictionary = None

    def __init__(self, language, session):
        self.language = language
        self.session = session

        # Index for DB <-> corpus
        try:
            self.index = gensim.utils.unpickle(PathUtility.CORPUS_INDEX(self.language))
            self.iterable = ArticleIterable(self.index, self.session)
        except:
            self.build_index()

        # Dictionary
        try:
            self.dictionary = Dictionary.load(self.language)
        except:
            self.build_dictionary()

    def __iter__(self):
        for document in self.iterable:
            yield self.dictionary.doc2bow(PreProcessor.run(document))

    def __getitem__(self, docno):
        article_id = self.index[docno]
        document = self.session.query(Article.text).filter(Article.id == article_id).first()[0]
        return self.dictionary.doc2bow(PreProcessor.run(document))

    def __len__(self):
        return len(self.index)

    def save(self):
        gensim.utils.pickle(obj=self.index,
                            fname=PathUtility.CORPUS_INDEX(self.language),
                            protocol=4)
        self.dictionary.save(self.language)

    def build_index(self):
        db_query = self.session.query(Article.id).join(Article.website).\
                                filter(Website.language == self.language).order_by(Article.id)
        self.index = [value[0] for value in db_query.all()]
        self.iterable = ArticleIterable(self.index, self.session)

    def build_dictionary(self):
        if self.iterable is None:
            self.build_index()

        self.dictionary = Dictionary()
        self.dictionary.add_documents(PreProcessor(self.iterable))

        # Remove stop words
        stop_ids = [self.dictionary.token2id[stopword]
                    for stopword in StopWords(self.language)
                    if stopword in self.dictionary.token2id]
        self.dictionary.filter_tokens(stop_ids)
        self.dictionary.compactify()


