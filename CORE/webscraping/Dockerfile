FROM python:3.6.0-alpine
MAINTAINER TCCPoli "tccpoli2017@gmail.com"

COPY . /usr/src/app
WORKDIR /usr/src/app

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
    && \
    pip install setup.py \
    && \
    wget -qO- https://raw.githubusercontent.com/codelucas/newspaper/master/download_corpora.py | python \
    && \
    apk del gcc build-base
