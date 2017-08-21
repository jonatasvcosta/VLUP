from celery import Celery


app = Celery('vlup_newscraper',
             broker='amqp://user:password@broker:5672/',
             timezone='America/Sao_Paulo',
             include=['vlup_newscraper.tasks'])


if __name__ == '__main__':
    app.start()
