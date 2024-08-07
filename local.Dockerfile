FROM amazoncorretto:21-alpine

ARG WEATHER_MODEL_PATH="/app/weather-model"
ARG WEATHER_TESTS_PATH="/app/weather-tests"

RUN apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add iputils-ping \
    && apk add curl \
    && apk add git \
    && apk add maven

# Debug port
EXPOSE 8011
# Server port
EXPOSE 8081

RUN git clone https://github.com/ljamesd15/weather-model.git $WEATHER_MODEL_PATH \
    && cd $WEATHER_MODEL_PATH \
    && mvn clean package \
    && mvn install:install-file \
         -Dfile=./target/weather-model-1.0-SNAPSHOT.jar \
         -DgroupId=com.weather \
         -DartifactId=weather-model \
         -Dversion=1.0-SNAPSHOT \
         -Dpackaging=jar \
         -DgeneratePom=true
RUN git clone https://github.com/ljamesd15/weather-tests.git $WEATHER_TESTS_PATH \
    && cd $WEATHER_TESTS_PATH \
    && mvn clean install -Dmaven.test.skip

COPY --chown=appuser:appuser ./docker/entrypoint-local.sh /home/appuser/bin/
WORKDIR /app
# Uncomment to debug issues with docker container file system setup
#CMD exec /bin/bash -c "trap : TERM INT; sleep infinity & wait"
ENTRYPOINT ["bash", "/home/appuser/bin/entrypoint-local.sh"]