import logging
import newspaper
from common.database import initialize_sql, DBSession
from newscraper.model import Article, Website
from newscraper.parser import extract_publish_date
from newscraper.validation import validate_article

fh = logging.FileHandler('/pythonlog/actions.log')
fh.setLevel(logging.DEBUG)

logging.basicConfig()
ours = logging.getLogger('newscraper.validation')
ours.setLevel(logging.DEBUG)
ours.addHandler(fh)
liblog = logging.getLogger('newspaper.article')
liblog.setLevel(logging.DEBUG)
liblog.addHandler(fh)

def reprocess():
    count = 0
    print("Connecting...")
    initialize_sql()
    try:
        for db_article in DBSession.query(Article).filter(Article.publish_date == None).yield_per(20):
            count += 1

            try:
                DBSession.begin()
                try:
                    newspaper_article = newspaper.Article(db_article.url, MIN_WORD_COUNT=50)
                    newspaper_article.download()
                    newspaper_article.parse()
                    newspaper_article.nlp()
                except:
                    ours.error("Failed: revisit article id {}".format(db_article.id))
                    DBSession.rollback()
                    continue

                db_article.text = newspaper_article.text
                db_article.title = newspaper_article.title
                db_article.url = newspaper_article.canonical_link or newspaper_article.url

                db_article.description = newspaper_article.meta_description[:500]
                db_article.image_url = newspaper_article.top_img
                db_article.publish_date = extract_publish_date(newspaper_article)

                if validate_article(newspaper_article, db_article.website, db_article.id):
                    print("{}: Updating {} - {}".format(count, newspaper_article.title, newspaper_article.url))
                else:
                    print("{}: Deleting {} - {}".format(count, newspaper_article.title, newspaper_article.url))
                    DBSession.delete(db_article)


                DBSession.commit()
            except Exception as e:
                ours.error("Failed: revisit article id {}".format(db_article.id))
                ours.error(e)


    except Exception as e:
        ours.error(e)
        DBSession.rollback()
        raise e

    DBSession.remove()

if __name__ == '__main__':
    reprocess()
