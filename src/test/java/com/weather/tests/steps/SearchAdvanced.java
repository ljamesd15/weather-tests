package com.weather.tests.steps;

import com.weather.model.external.SensorMetadata;
import com.weather.model.external.request.SaveWeatherDataRequest;
import com.weather.model.external.request.SearchWeatherDataRequest;
import com.weather.model.external.response.SaveWeatherDataResponse;
import com.weather.model.external.response.SearchWeatherDataResponse;
import com.weather.tests.client.WeatherPlatformClient;
import com.weather.tests.enums.TimeConstant;
import com.weather.tests.util.DateTimeUtil;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.weather.tests.fixtures.WeatherDataFixtures.SAVE_WEATHER_DATA_REQUEST;
import static com.weather.tests.fixtures.WeatherDataFixtures.SENSOR_METADATA_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAdvanced {
    private SearchWeatherDataResponse searchWeatherDataResponse;
    private final Set<String> createdIds = new HashSet<>();

    @Autowired
    WeatherPlatformClient client;

    @Autowired
    DateTimeUtil dateTimeUtil;

    @ParameterType("YESTERDAY|TODAY|NOW|TOMORROW")
    public TimeConstant timeConstant(String timeConstant) {
        return TimeConstant.valueOf(timeConstant);
    }

    @Given("I have weather data from sensor {string} at time {timeConstant}")
    public void saveWeatherDataForSensor(String sensorId, TimeConstant time) {
        SensorMetadata sensorMetadata = SENSOR_METADATA_1.toBuilder()
                .sensorId(sensorId)
                .build();
        SaveWeatherDataRequest request = SAVE_WEATHER_DATA_REQUEST.toBuilder()
                .sensorMetadata(sensorMetadata)
                .time(dateTimeUtil.timeConstantToDateTime(time))
                .build();
        SaveWeatherDataResponse response = client.saveWeatherData(request);
        assertEquals(sensorId, response.weatherData().sensorMetadata().sensorId());
        createdIds.add(response.weatherData().id());
    }

    @Given("I have weather data from sensor {string} at location {string}")
    public void saveWeatherDataForSensor(String sensorId, String location) {
        SensorMetadata sensorMetadata = SENSOR_METADATA_1.toBuilder()
                .sensorId(sensorId)
                .location(location)
                .build();
        SaveWeatherDataRequest request = SAVE_WEATHER_DATA_REQUEST.toBuilder()
                .sensorMetadata(sensorMetadata)
                .build();
        SaveWeatherDataResponse response = client.saveWeatherData(request);
        assertEquals(sensorId, response.weatherData().sensorMetadata().sensorId());
        createdIds.add(response.weatherData().id());
    }

    @When("I search for weather data from sensor {string} from time {timeConstant} to {timeConstant}")
    public void searchWeatherDataBetweenTimes(String sensorId, TimeConstant from, TimeConstant to) {
        SearchWeatherDataRequest request = SearchWeatherDataRequest.builder()
                .sensorId(sensorId)
                .minTime(dateTimeUtil.timeConstantToDateTime(from))
                .maxTime(dateTimeUtil.timeConstantToDateTime(to))
                .build();
        this.searchWeatherDataResponse = this.client.searchWeatherData(request);
    }

    @When("I search for weather data from sensor {string} at location {string}")
    public void searchWeatherDataAtLocation(String sensorId, String location) {
        SearchWeatherDataRequest request = SearchWeatherDataRequest.builder()
                .sensorId(sensorId)
                .location(location)
                .minTime(ZonedDateTime.now().minusMinutes(5))
                .maxTime(ZonedDateTime.now())
                .build();
        this.searchWeatherDataResponse = this.client.searchWeatherData(request);
    }

    @Then("I see {int} result from sensor {string} before {timeConstant}")
    public void nResultsForSensor(int n, String sensorId, TimeConstant time) {
        assertEquals(n, this.searchWeatherDataResponse.weatherDataList().size());
        assertTrue(this.searchWeatherDataResponse.weatherDataList()
                .stream()
                .anyMatch(weatherData -> sensorId.equals(weatherData.sensorMetadata().sensorId())
                    && dateTimeUtil.timeConstantToDateTime(time).isAfter(weatherData.time())));
    }

    @Then("I see {int} result from sensor {string} at location {string}")
    public void nResultsForSensor(int n, String sensorId, String location) {
        assertEquals(n, this.searchWeatherDataResponse.weatherDataList().size());
        assertTrue(this.searchWeatherDataResponse.weatherDataList()
                .stream()
                .anyMatch(weatherData -> sensorId.equals(weatherData.sensorMetadata().sensorId())
                        && location.equals(weatherData.sensorMetadata().location())));
    }

    @Then("I do not see any results - advanced")
    public void noResults() {
        assertTrue(searchWeatherDataResponse.weatherDataList().isEmpty());
    }

    @After
    public void cleanup() {
        createdIds.forEach(this.client::deleteWeatherData);
    }
}
