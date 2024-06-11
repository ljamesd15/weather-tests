package com.weather.tests.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        glue = "com.weather.tests.steps",
        features = "src/test/resources/cucumber/features",
        plugin = {"pretty", "json:target/cucumber-report.json"}
)
@SpringBootTest
public class WeatherPlatformRunnerTest {
}
