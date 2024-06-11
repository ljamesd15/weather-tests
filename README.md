# Weather Tests
This repository contains all the integration & end-to-end tests for the weather suite of services

## Running integration tests
You can run individual features like
```
$ mvn test -Dspring.profiles.active=local,ide \
    -Dsurefire.includeJUnit5Engines=cucumber \
    -Dcucumber.plugin=pretty \
    -Dcucumber.features=src/test/resources/cucumber/features/WeatherBasic.feature
```
Or run the entire suite with
```$ mvn clean test -Dspring.profiles.active=local,ide```
Or to when running the package step
```$ mvn clean package -Dspring.profiles.active=local,ide```