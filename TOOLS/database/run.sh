#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker stop database
docker rm database

docker run -d --name database \
    -v $DIR/database/init:/docker-entrypoint-initdb.d \
    -e MYSQL_ROOT_PASSWORD=password \
    -e MYSQL_DATABASE=vlup-db \
    mysql:5.7
