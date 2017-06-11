from celery import Celery


app = Celery('vlup_newscraper',
             broker='amqp://user:password@broker:5672/')

if __name__ == '__main__':
    app.start()
