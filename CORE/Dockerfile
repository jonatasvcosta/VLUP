FROM python:3.6-alpine3.6
LABEL TCCPoli "tccpoli2017@gmail.com"

COPY requirements.txt /app/requirements.txt
COPY wheels /wheels
WORKDIR /app
ENV HOME /home/celery

RUN apk update \
    && \
    apk add --no-cache \
            libxml2-dev \
            libxslt-dev \
            libjpeg-turbo-dev \
            zlib \
            zlib-dev \
            libpng-dev \
            jpeg-dev \
            postgresql-dev \
            libstdc++ \
            openblas-dev \
    && \
    apk add --no-cache --virtual .build-deps \
            git \
            build-base \
            gcc \
            python3-dev \
    && \
    apk add --update openssl \
    && \
    pip install -r requirements.txt --find-links=/wheels --only-binary gensim,numpy,scipy \
    && \
    wget -qO- https://raw.githubusercontent.com/codelucas/newspaper/master/download_corpora.py | python \
    && \
    apk del --purge -r .build-deps && rm -rf /wheels

RUN adduser -D -g '' -u 1000 celery
