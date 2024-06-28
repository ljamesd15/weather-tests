Feature: Weather Search Advanced Testing

  Scenario: Time Based Search
    Given I have weather data from sensor "123" at time NOW
    When I search for weather data from sensor "123" from time YESTERDAY to TOMORROW
    Then I see 1 result from sensor "123" before NOW
    When I search for weather data from sensor "123" from time NOW to TOMORROW
    Then I do not see any results - advanced
    When I search for weather data from sensor "123" from time YESTERDAY to TODAY
    Then I do not see any results - advanced

  Scenario: Location Based Search
    Given I have weather data from sensor "123" at location "greenhouse"
    When I search for weather data from sensor "123" at location "greenhouse"
    Then I see 1 result from sensor "123" at location "greenhouse"
    When I search for weather data from sensor "123" at location "farmhouse"
    Then I do not see any results - advanced