import langdetect
import logging
import tldextract
from common.database import DBSession
from .model import Article
from urllib.parse import urlparse, urlunparse

log = logging.getLogger(__name__)

def validate_article(article, website, article_id=None):

    if len(article.text) < 140:
        log.debug("{} text is too short".format(get_article_url(article)))
        return False

    if not article.is_valid_body():
        return False

    if article.is_media_news():
        log.debug("{} is of type 'media'".format(get_article_url(article)))
        return False

    # Regra: detectar lingua e ver se bate
    detected_language = langdetect.detect(article.text)
    if detected_language != website.language:
        log.debug("{} language is '{}' instead of '{}'".format(get_article_url(article), detected_language, website.language))
        return False

    # Regra: se website começa com www., pode entrar em subdomínios.
    # Se website começa tem um subdominio específico, então restringe a esse subdomínio
    # Motivo: alguns sites usam subdomínios como categorias, outros usam como jornais separados.
    website_parsed = tldextract.extract(website.url)
    article_parsed = tldextract.extract(get_article_url(article))

    if website_parsed[1:] != article_parsed[1:]:
        # Domínios diferentes
        log.debug("{} doesn't match domain: {}".format(get_article_url(article), '.'.join(website_parsed)))
        return False

    if website_parsed.subdomain != 'www' and article_parsed.subdomain != website_parsed.subdomain:
        # Subdomínios diferentes e não começa com www
        log.debug("{} is not an allowed subdomain of {}".format(get_article_url(article), '.'.join(website_parsed)))
        return False


    # Check DB for duplicates
    http = set_url_scheme(get_article_url(article), 'http')
    https = set_url_scheme(get_article_url(article), 'https')

    if article_id is None:
        dup = DBSession.query(Article).filter( (Article.url == http) | (Article.url == https) ).first()
    else:
        dup = DBSession.query(Article).filter(Article.id != article_id).filter( (Article.url == http) | (Article.url == https) ).first()

    if dup is not None:
        log.debug("{} duplicate found with id {}".format(get_article_url(article), dup.id))
        return False

    return True



def set_url_scheme(url, scheme):
    url_fragments = urlparse(url)
    new_url_fragments = ( scheme, *url_fragments[1:] )
    return urlunparse( new_url_fragments )


def get_article_url(article):
    return article.canonical_link or article.url
