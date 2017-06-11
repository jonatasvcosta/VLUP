from . import app


@app.task
def check_news():
    print(100)
