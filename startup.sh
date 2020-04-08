#!/bin/bash

cd ./iea/
cp ./src/main/docker/Dockerfile.jvm ./Dockerfile
mvn clean install
cd ../

docker-compose build --parallel
docker-compose up
