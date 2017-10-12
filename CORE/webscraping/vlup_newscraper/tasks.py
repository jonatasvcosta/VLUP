import newspaper
import celery
from .celery import app
from .model import DBSession, Article, Website, initialize_sql
from celery.schedules import crontab


@app.on_after_finalize.connect
def setup_periodic_tasks(sender, **kwargs):
    # Executes every 3 hours
    sender.add_periodic_task(
        crontab(minute=0, hour='*/3'),
        check_news.s(),
    )


class SqlAlchemyTask(celery.Task):
    """An abstract Celery Task that ensures that the connection the the
    database is closed on task completion"""
    abstract = True

    def after_return(self, status, retval, task_id, args, kwargs, einfo):
        DBSession.remove()


@app.task(name='vlup.check_news', base=SqlAlchemyTask)
def check_news():
    initialize_sql()
    websites = DBSession.query(Website).all()
    for website in websites:
        download_website.delay(website.id)


@app.task(name='vlup.download_website', base=SqlAlchemyTask)
def download_website(website_id):
    try:
        count = 0
        website = DBSession.query(Website).get(website_id)
        print("Downloading {}".format(website.url))
        news = newspaper.build(website.url, memoize_articles=False)
        for article in news.articles:
            count += save_article(article, website)
        print("Downloaded {} articles from {}".format(count, website.url))
    except:
        print("Could not find website #{}".format(website_id))


def save_article(article, website):
    try:
        article.download()
        article.parse()

        obj = Article()
        obj.text = article.text
        obj.title = article.title
        obj.url = article.url
        obj.website = website
        DBSession.add(obj)
        return 1
    except Exception as error:
        print("Error: {} - {}".format(article.url, error))
        return 0
