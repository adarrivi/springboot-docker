#!/usr/bin/env bash
echo "stopping and removing docker container"
pwd
rm -f docker/springboot-docker-ws.jar
docker stop spring-docker
docker rm spring-docker