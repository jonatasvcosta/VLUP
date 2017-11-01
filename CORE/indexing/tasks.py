import os
from common.celery import app, SqlAlchemyTask
from common.database import DBSession, initialize_sql
from .corpus import DbCorpus
from .similarity import SimilarityLSA
from . import PathUtility


@app.task(base=SqlAlchemyTask)
def build_similarity():
    initialize_sql()

    for language in ["pt", "en"]:
        # Make sure folder exists
        directory = os.path.dirname(PathUtility.SIMILARITY(language))
        if not os.path.exists(directory):
            os.makedirs(directory)

        corpus = DbCorpus(language=language,
                          session=DBSession)
        corpus.build_index()
        corpus.build_dictionary()
        corpus.save()

        similarity = SimilarityLSA(corpus, language)
        similarity.build()
        similarity.save()
