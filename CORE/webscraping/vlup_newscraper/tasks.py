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
    count = 1
    websites = DBSession.query(Website).all()
    site = newspaper.build(websites[0].url)
    for article in site.articles:
        DBSession.begin()
        article.download()
        article.parse()

        print("{} - {}".format(count, article.title))
        count += 1
        save_article(article, websites[0])
        DBSession.commit()


def save_article(article, website):
    obj = Article()
    obj.text = article.text
    obj.title = article.title
    obj.url = article.url
    obj.website = website
    DBSession.add(obj)
