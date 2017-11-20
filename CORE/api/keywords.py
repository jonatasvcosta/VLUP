import werkzeug.exceptions as exceptions
from flask import Blueprint, request, jsonify
from common.database import DBSession
from newscraper.model import Article

from indexing.keywords import KeywordsExtractor
from indexing import available_languages

bp = Blueprint('keywords', __name__)


@bp.route('/')
def extract_keywords():
    id_list = request.args.getlist('article_id')
    language = request.args.get('language', '')

    try:
        id_list = [int(x) for x in id_list]
    except:
        raise exceptions.BadRequest("Error in 'article_id'")

    try:
        limit = int(request.args.get('limit', 15))
    except:
        limit = 15

    if language not in available_languages:
        raise exceptions.BadRequest("Language '{}' is not supported".format(language))

    ke = KeywordsExtractor(language)

    result = [
                {
                    "article_id": article.id,
                    "keywords": ke.keywords(article.text, limit)
                }

               for article in DBSession.query(Article).filter(Article.id.in_(id_list))
             ]


    try:
        return jsonify(result=result)
    except:
        raise exceptions.BadRequest("Query too short")
