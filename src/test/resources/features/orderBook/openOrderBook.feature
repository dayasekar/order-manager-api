Feature: Enable users to OPEN a closed order book

  Scenario: Open an order book for a financial instrument that already exists
    Given There exists an existing closed order book with instrument id fx_swap
    When the rest client invokes /order-book/open PUT endpoint
    Then the open order api responds with status code of 200 and success response

  Scenario: Open an order book for a financial instrument that is already OPEN
    Given There exists an existing closed order book with status OPEN and instrument id fx_swap
    When the rest client invokes /order-book/open PUT endpoint
    Then the open order api responds with status code of 500 and response of error messages


