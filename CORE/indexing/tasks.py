from common.celery import app, SqlAlchemyTask
from common import DBSession, initialize_sql
from .corpus import DbCorpus
from .similarity import SimilarityLSA

@app.task(base=SqlAlchemyTask)
def build_similarity():
    initialize_sql()

    for language in ["pt", "en"]:
        corpus = DbCorpus(language=language,
                          session=DBSession)
        corpus.build_dictionary()
        corpus.save()

        similarity = SimilarityLSA(corpus, language)
        similarity.build()
        similarity.save()
