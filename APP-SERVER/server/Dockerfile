FROM python:3.6.0-alpine
MAINTAINER TCCPoli "tccpoli2017@gmail.com"

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN apk --no-cache add musl-dev linux-headers g++ \
    && python setup.py egg_info \
    && python setup.py install \
    && apk del musl-dev linux-headers g++


CMD ["gunicorn", "--paste", "production.ini"]
