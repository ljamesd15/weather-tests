FROM amazoncorretto:21-alpine

RUN apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add iputils-ping \
    && apk add git \
    && apk add maven

# Debug port
EXPOSE 8001
# Server port
EXPOSE 8081

RUN git clone https://github.com/ljamesd15/weather-model.git \
    && cd weather-model/ \
    && mvn clean package \
    && mvn install:install-file \
         -Dfile=./target/weather-model-1.0-SNAPSHOT.jar \
         -DgroupId=com.weather \
         -DartifactId=weather-model \
         -Dversion=1.0-SNAPSHOT \
         -Dpackaging=jar \
         -DgeneratePom=true
RUN git clone https://github.com/ljamesd15/weather-tests.git \
    && cd weather-tests/ \
    && mvn clean install -Dmaven.test.skip

COPY --chown=appuser:appuser ./docker/entrypoint-local.sh /home/appuser/bin/
COPY ./target /app
WORKDIR /app
ENTRYPOINT ["bash", "/home/appuser/bin/entrypoint-local.sh"]