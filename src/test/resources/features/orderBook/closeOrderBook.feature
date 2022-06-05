Feature: Enable users to close an open order book

  Scenario: Close an order book for a financial instrument that already exists
    Given There exists an existing order book with instrument id fx_swap
    When the rest client invokes /order-book/close POST endpoint
    Then the close order api responds with status code of 200 and success response

  Scenario: Close an order book for a financial instrument that is already closed
    Given There exists an existing order book with status CLOSED and instrument id fx_swap
    When the rest client invokes /order-book/close POST endpoint
    Then the close order api responds with status code of 500 and response of error messages


