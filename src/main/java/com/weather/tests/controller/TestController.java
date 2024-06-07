package com.weather.tests.controller;

import com.weather.model.external.request.SaveWeatherDataRequest;
import com.weather.model.external.request.SearchWeatherDataRequest;
import com.weather.model.external.response.SaveWeatherDataResponse;
import com.weather.model.external.response.SearchWeatherDataResponse;
import com.weather.tests.client.WeatherPlatformClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    private final WeatherPlatformClient client;

    public TestController(WeatherPlatformClient client) {
        this.client = client;
    }

    @PostMapping("/save")
    public ResponseEntity<SaveWeatherDataResponse> save(@RequestBody SaveWeatherDataRequest request) {
        try {
            return ResponseEntity.ok(this.client.saveWeatherData(request));
        } catch (Exception ex) {
            log.error("Encountered error while saving weather data", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<SearchWeatherDataResponse> search(@RequestBody SearchWeatherDataRequest request) {
        try {
            return ResponseEntity.ok(this.client.searchWeatherData(request));
        } catch (Exception ex) {
            log.error("Encountered error while searching weather data", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
