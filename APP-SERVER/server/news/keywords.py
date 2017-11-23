import requests


endpoint = "http://core:8000/keywords"

def get_keywords(article_id, language, limit):
    response = requests.get(endpoint, params={"article_id": article_id, "language": language, "limit": limit})

    return response.json()['result']
