import werkzeug.exceptions as exceptions
from flask import Blueprint, request, jsonify
from common.database import DBSession

from indexing import available_languages
from indexing.corpus import DbCorpus
from indexing.similarity import SimilarityLSA

bp = Blueprint('similarity', __name__)


@bp.route('/')
def query_similarity():
    query = request.args.get('query', '')
    language = request.args.get('language', '')

    try:
        limit = int(request.args.get('limit', 15))
    except:
        limit = 15

    if language not in available_languages:
        raise exceptions.BadRequest("Language '{}' is not supported".format(language))

    corpus = DbCorpus(language=language, session=DBSession)
    sim = SimilarityLSA(corpus=corpus, language=language)

    try:
        return jsonify(result=[(corpus.index[docno], score) for docno, score in sim.query(query, limit)])
    except:
        raise exceptions.BadRequest("Query too short")
