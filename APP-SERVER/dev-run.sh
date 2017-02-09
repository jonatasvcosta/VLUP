#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Building development image"
docker build server \
    -f server/Dockerfile.dev \
    -t dasz-api

echo -e "\nStopping containers"
docker stop dasz-api
docker stop reverseproxy
docker stop database

echo -e "\nRemoving containers"
docker rm dasz-api
docker rm reverseproxy
docker rm database

echo -e "\nRunning database"
docker run -d --name database \
    -v $DIR/database/data:/var/lib/mysql \
    -v $DIR/database/init:/docker-entrypoint-initdb.d \
    -e MYSQL_ROOT_PASSWORD=password \
    -e MYSQL_DATABASE=dasz-db \
    mysql:5.7

echo -e "\nRunning server"
docker run -d --name dasz-api \
    -v $DIR/server:/usr/src/app \
    --link database \
    dasz-api

echo -e "\nRunning reverseproxy"
docker run -d --name reverseproxy \
    -v $DIR/reverseproxy:/etc/nginx/ \
    --link dasz-api \
    -p 80:80 \
    nginx:1.11.9-alpine
