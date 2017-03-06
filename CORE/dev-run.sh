#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Building development image"
docker build webscraping \
    -f webscraping/Dockerfile.dev \
    -t vlup-spider

echo -e "\nStopping containers"
docker stop vlup-spider

echo -e "\nRemoving containers"
docker rm vlup-spider

echo -e "\nRunning spider"
docker run --name vlup-spider \
    -v $DIR/webscraping:/usr/src/app \
    vlup-spider
