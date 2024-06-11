package com.weather.tests.fixtures;

import com.weather.model.external.SensorMetadata;
import com.weather.model.external.WeatherData;
import com.weather.model.external.enums.Direction;
import com.weather.model.external.request.SaveWeatherDataRequest;

import java.time.ZonedDateTime;

public interface WeatherDataFixtures {

    // Sensor metadata fields
    String SENSOR_ID_1 = "sensor123";
    String LOCATION_1 = "Greenhouse";
    double LATITUDE_1 = 10.1;
    double LONGITUDE_1 = 20.2;

    // Weather data fields
    String ID_1 = "66678d1d34ead05a95aa399c";
    double HUMIDITY_1 = 30.0;
    double LUMINOSITY_1 = 100.0;
    double PRESSURE_1 = 50.0;
    double TEMPERATURE_1 = 40.0;
    double UV_INDEX_1 = 5.3;
    double WIND_SPEED_1 = 12.2;

    SensorMetadata SENSOR_METADATA_1 = SensorMetadata.builder()
            .sensorId(SENSOR_ID_1)
            .latitude(LATITUDE_1)
            .longitude(LONGITUDE_1)
            .location(LOCATION_1)
            .build();

    WeatherData WEATHER_DATA_1 = WeatherData.builder()
            .id(ID_1)
            .humidity(HUMIDITY_1)
            .luminosity(LUMINOSITY_1)
            .pressure(PRESSURE_1)
            .temperature(TEMPERATURE_1)
            .time(ZonedDateTime.now())
            .uvIndex(UV_INDEX_1)
            .windDirection(Direction.W)
            .windSpeed(WIND_SPEED_1)
            .sensorMetadata(SENSOR_METADATA_1)
            .build();

    SaveWeatherDataRequest SAVE_WEATHER_DATA_REQUEST = SaveWeatherDataRequest.builder()
            .humidity(HUMIDITY_1)
            .luminosity(LUMINOSITY_1)
            .pressure(PRESSURE_1)
            .temperature(TEMPERATURE_1)
            .time(ZonedDateTime.now())
            .uvIndex(UV_INDEX_1)
            .windDirection(Direction.W)
            .windSpeed(WIND_SPEED_1)
            .sensorMetadata(SENSOR_METADATA_1)
            .build();
}
