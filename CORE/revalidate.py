import newspaper
from common.database import initialize_sql, DBSession
from newcraper.model import Article, Website
from newscraper.validation import validate_article


def reprocess():
    for db_article in DBSession.query(Article).all():
        newspaper_article = newspaper.Article(db_article.url)
        newspaper_article.download()
        newspaper_article.parse()
        newspaper_article.nlp()

        db_article.text = newspaper_article.text
        db_article.title = newspaper_article.title
        db_article.url = newspaper_article.canonical_link or article.url

        db_article.description = newspaper_article.meta_description
        db_article.image_url = newspaper_article.top_img
        db_article.publish_date = newspaper_article.publish_date

        if validate_article(article, website):
            print("{} - {}".format(article.title, article.url))

            DBSession.begin()
            DBSession.add(obj)
            DBSession.commit()
        else:

