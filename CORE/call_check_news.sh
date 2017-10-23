#!/bin/sh

docker-compose run worker celery -A newscraper.celery call vlup.check_news
