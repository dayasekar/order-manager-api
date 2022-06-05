Feature: Enable users to create an new order book with OPEN status

  Scenario: Create a new order book for a financial instrument that does not exist
    Given The instrument id of the order book is fx_swap
    When the rest client invokes /order-book/create POST endpoint
    Then the api responds with status code of 200 and response of newly created order book

  Scenario: Create a new order book for a financial instrument that already exists
    Given The instrument id of the order book is fx_swap and instrument id of the existing order book is fx_swap
    When the rest client invokes /order-book/create POST endpoint
    Then the api responds with status code of 500 and response of error messages

