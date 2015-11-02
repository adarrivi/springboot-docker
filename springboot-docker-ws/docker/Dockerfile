FROM andreptb/oracle-java:8
MAINTAINER adarrivi

VOLUME /tmp
ADD springboot-docker-ws.jar app.jar
EXPOSE 8080
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
