import newspaper
import celery
from common.celery import app, SqlAlchemyTask
from common.database import DBSession, initialize_sql
from .model import Article, Website
from celery.schedules import crontab
from .validation import validate_article
from .parser import extract_publish_date


@app.on_after_finalize.connect
def setup_periodic_tasks(sender, **kwargs):
    # Executes every 3 hours
    sender.add_periodic_task(
        crontab(minute=0, hour='*/8'),
        check_news.s(),
    )


@app.task(name='vlup.check_news', base=SqlAlchemyTask)
def check_news():
    initialize_sql()
    websites = DBSession.query(Website).all()
    for website in websites:
        download_website.delay(website.id)


@app.task(name='vlup.download_website', base=SqlAlchemyTask)
def download_website(website_id):
    initialize_sql()
    try:
        count = 0
        website = DBSession.query(Website).get(website_id)
        print("Downloading {}".format(website.url))
        news = newspaper.build(website.url, language=website.language, memoize_articles=False)
        for article in news.articles:
            count += save_article(article, website)
        print("Downloaded {} articles from {}".format(count, website.url))
    except Exception as error:
        print("Could not download website #{}: \n{}".format(website_id, error))


def save_article(article, website):
    try:
        article.download()
        article.parse()
        article.nlp()

        obj = Article()
        obj.text = article.text
        obj.title = article.title
        obj.url = article.canonical_link or article.url

        obj.description = article.meta_description
        obj.image_url = article.top_img
        obj.publish_date = extract_publish_date(article)

        obj.website = website

        if validate_article(article, website):
            print("{} - {}".format(article.title, article.url))

            DBSession.begin()
            DBSession.add(obj)
            DBSession.commit()
            return 1
    except Exception as error:
        DBSession.rollback()
        print("Error: {} - {}".format(article.url, error))

    return 0
