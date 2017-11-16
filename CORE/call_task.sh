#!/bin/sh

docker-compose run worker celery -A common.celery call $1
