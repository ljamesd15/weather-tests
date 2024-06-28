# Weather Tests
This repository contains all the integration & end-to-end tests for the weather suite of services

## Running integration tests
You can run individual features like
```
$ mvn test -Dspring.profiles.active=local,ide \
    -Dsurefire.includeJUnit5Engines=cucumber \
    -Dcucumber.plugin=pretty \
    -Dcucumber.features=src/test/resources/cucumber/features/WeatherSearchSaveBasic.feature
```
Or run the entire suite with
```$ mvn clean test -Dspring.profiles.active=local,ide```
Or to when running the package step
```$ mvn clean package -Dspring.profiles.active=local,ide```

## Building images

To ensure you get the latest versions of all repos required to run the tests its recommend to use the ```--no-cache``` option when building the images.

### Local
Local image building and running is done through docker compose
```$ docker compose build && docker compose up```

## Standard
Everything else uses a standard Dockerfile composition
```$ docker build . -t weather-tests``` (or to ensure you pull the latest every time ```$ docker build . -t weather-tests --no-cache```)
```$ docker run -e SPRING_PROFILES_ACTIVE='<ENV>' weather-tests:latest```