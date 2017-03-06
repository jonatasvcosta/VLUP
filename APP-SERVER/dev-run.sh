#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Building development image"
docker build server \
    -f server/Dockerfile.dev \
    -t vlup-api

echo -e "\nStopping containers"
docker stop vlup-api
docker stop reverseproxy
docker stop database

echo -e "\nRemoving containers"
docker rm vlup-api
docker rm reverseproxy
docker rm database

echo -e "\nRunning database"
docker run -d --name database \
    -v $DIR/database/init:/docker-entrypoint-initdb.d \
    -e MYSQL_ROOT_PASSWORD=password \
    -e MYSQL_DATABASE=vlup-db \
    mysql:5.7

echo -e "\nRunning server"
docker run -d --name vlup-api \
    -v $DIR/server:/usr/src/app \
    --link database \
    vlup-api

echo -e "\nRunning reverseproxy"
docker run -d --name reverseproxy \
    -v $DIR/reverseproxy:/etc/nginx/ \
    --link vlup-api \
    -p 80:80 \
    nginx:1.11.9-alpine
