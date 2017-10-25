from celery import Celery, Task
from .database import DBSession


app = Celery('core',
             broker='amqp://user:password@broker:5672/',
             timezone='America/Sao_Paulo',
             include=['newscraper.tasks', 'indexing.tasks'])


class SqlAlchemyTask(Task):
    """An abstract Celery Task that ensures that the connection the the
    database is closed on task completion"""
    abstract = True

    def after_return(self, status, retval, task_id, args, kwargs, einfo):
        DBSession.remove()


if __name__ == '__main__':
    app.start()
