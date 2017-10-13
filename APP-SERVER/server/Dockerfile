FROM python:3.6-alpine3.6
LABEL TCCPoli "tccpoli2017@gmail.com"

COPY setup.py /app/setup.py
WORKDIR /app

RUN apk --no-cache add musl-dev linux-headers gcc postgresql-dev \
    && python setup.py install \
    && apk del musl-dev linux-headers gcc \
    && git clone https://github.com/gutfeeling/word_forms.git \
    && pip install -e word_forms

ENV DJANGO_PRODUCTION=true
ENV DJANGO_SETTINGS_MODULE="vlup.settings_production"
ENV ROOT_PASSWORD=vluptcc
ENV GUNICORN_CMD_ARGS="--bind=0.0.0.0:8000 --workers=4"

COPY . /app

CMD "/app/init.sh"

