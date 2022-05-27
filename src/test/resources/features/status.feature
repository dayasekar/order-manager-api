Feature: Test application is alive and responding

  Scenario: Test application is alive
    Given The service is up and running
    When the rest client invokes /ping endpoint
    Then the api responds with status code of 200 and response string pong
