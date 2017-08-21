FROM python:3.6.0-alpine
MAINTAINER TCCPoli "tccpoli2017@gmail.com"

COPY requirements.txt /app/requirements.txt
WORKDIR /app
ENV HOME /home/celery

RUN apk add --no-cache \
    build-base \
    gcc \
    python-dev \
    libxml2-dev \
    libxslt-dev \
    libjpeg-turbo-dev \
    zlib \
    zlib-dev \
    libpng-dev \
    jpeg-dev \
    postgresql-dev \
    && \
    pip install -r requirements.txt \
    && \
    wget -qO- https://raw.githubusercontent.com/codelucas/newspaper/master/download_corpora.py | python \
    && \
    apk del gcc build-base

RUN adduser -D -g '' -u 1000 celery
