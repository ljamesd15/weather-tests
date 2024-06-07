# Weather Tests
This repository contains all the integration & end-to-end tests for the weather suite of services

## Local development
To start the service
1. ```mvn clean package```
2. ```$ java -jar -Dspring.profiles.active=local target/weather-platform-0.0.1-SNAPSHOT.jar com.weather.platform.WeatherPlatformApplication.java```

## Making a change
1. Make changes necessary and add unit tests
2. Run the new unit tests to ensure they pass
   1. Ex. ```mvn test -Dtest=WeatherServiceTest#saveWeatherDataTest```
3. Run all unit tests to ensure nothing broke ```mvn clean test```
4. Start service (see above) and manually smoke test changes
5. Add changes with a meaningful commit message
6. Push changes to a feature branch and submit a pull request to main branch