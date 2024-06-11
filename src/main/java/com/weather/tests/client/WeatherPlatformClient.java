package com.weather.tests.client;

import com.weather.model.external.request.SaveWeatherDataRequest;
import com.weather.model.external.request.SearchWeatherDataRequest;
import com.weather.model.external.response.SaveWeatherDataResponse;
import com.weather.model.external.response.SearchWeatherDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.weather.tests.constants.ApiConstants.DELETE_WEATHER_DATA_PATH;
import static com.weather.tests.constants.ApiConstants.SAVE_WEATHER_DATA_PATH;
import static com.weather.tests.constants.ApiConstants.SEARCH_WEATHER_DATA_PATH;

@Component
@Slf4j
public class WeatherPlatformClient {

    private final RestClient restClient;

    public WeatherPlatformClient(@Qualifier("WeatherService") final RestClient restClient) {
        this.restClient = restClient;
    }

    public SearchWeatherDataResponse searchWeatherData(final SearchWeatherDataRequest request) {
        return this.restClient.post()
                .uri(SEARCH_WEATHER_DATA_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(SearchWeatherDataResponse.class)
                .getBody();
    }

    public SaveWeatherDataResponse saveWeatherData(final SaveWeatherDataRequest request) {
        return this.restClient.post()
                .uri(SAVE_WEATHER_DATA_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(SaveWeatherDataResponse.class)
                .getBody();
    }

    public void deleteWeatherData(String id) {
        this.restClient.delete()
                .uri(DELETE_WEATHER_DATA_PATH, id)
                .retrieve()
                .toBodilessEntity();
    }
}
