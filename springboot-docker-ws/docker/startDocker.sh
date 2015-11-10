#!/usr/bin/env bash
echo "building and starting docker container"
pwd
cp target/springboot-docker-ws.jar docker/
docker build -t "spring-docker-ws:dockerfile" docker/.
docker run -d --name="spring-docker" -p 8080:8080 spring-docker-ws:dockerfile