package com.weather.tests.steps;

import com.weather.model.external.SensorMetadata;
import com.weather.model.external.request.SaveWeatherDataRequest;
import com.weather.model.external.request.SearchWeatherDataRequest;
import com.weather.model.external.response.SaveWeatherDataResponse;
import com.weather.model.external.response.SearchWeatherDataResponse;
import com.weather.tests.client.WeatherPlatformClient;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.weather.tests.fixtures.WeatherDataFixtures.SAVE_WEATHER_DATA_REQUEST;
import static com.weather.tests.fixtures.WeatherDataFixtures.SENSOR_METADATA_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest
public class SearchSaveBasic {

    private SearchWeatherDataResponse searchWeatherDataResponse;
    private final Set<String> createdIds = new HashSet<>();

    @Autowired
    WeatherPlatformClient client;

    @Given("I have nothing")
    public void iHaveNothing() {
    }

    @Given("I have weather data from sensor {string}")
    public void saveWeatherDataForSensor(String sensorId) {
        SensorMetadata sensorMetadata = SENSOR_METADATA_1.toBuilder()
                .sensorId(sensorId)
                .build();
        SaveWeatherDataRequest request = SAVE_WEATHER_DATA_REQUEST.toBuilder()
                        .sensorMetadata(sensorMetadata)
                                .build();
        SaveWeatherDataResponse response = client.saveWeatherData(request);
        assertEquals(sensorId, response.weatherData().sensorMetadata().sensorId());
        createdIds.add(response.weatherData().id());
    }

    @When("I search for weather data from sensor {string}")
    public void searchWeatherDataForSensor(String sensorId) {
        SearchWeatherDataRequest request = SearchWeatherDataRequest.builder()
                .sensorId(sensorId)
                .minTime(ZonedDateTime.now().minusMinutes(5))
                .maxTime(ZonedDateTime.now())
                .build();
        this.searchWeatherDataResponse = this.client.searchWeatherData(request);
    }

    @Then("I see {int} result from sensor {string}")
    public void nResultsForSensor(int n, String sensorId) {
        assertEquals(n, this.searchWeatherDataResponse.weatherDataList().size());
        assertTrue(this.searchWeatherDataResponse.weatherDataList()
                .stream()
                .anyMatch(weatherData -> sensorId.equals(weatherData.sensorMetadata().sensorId())));
    }

    @Then("I do not see any results")
    public void noResults() {
        assertTrue(searchWeatherDataResponse.weatherDataList().isEmpty());
    }

    @After
    public void cleanup() {
        createdIds.forEach(this.client::deleteWeatherData);
    }
}
