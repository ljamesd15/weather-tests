Feature: Weather Search/Save Testing

  Scenario: No results
    Given I have nothing
    When I search for weather data from sensor "123"
    Then I do not see any results
  Scenario: Save and Search
    Given I have weather data from sensor "123"
    When I search for weather data from sensor "123"
    Then I see 1 result from sensor "123"
