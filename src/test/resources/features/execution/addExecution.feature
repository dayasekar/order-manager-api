Feature: Enable users to add an new execution

  Scenario: Create a new execution for a financial instrument that exists with status closed
    Given A sample execution with price as 100 and existing instrument id as fx_swap and other mandatory fields
    When the rest client invokes /execution/add POST endpoint
    Then the add execution api responds with status code of 200 and response of newly created execution

  Scenario: Create a new limit order for a financial instrument that exists with status open
    Given A sample order with price as 100 and existing instrument id as fx_swap, status open and other mandatory fields
    When the rest client invokes /execution/add POST endpoint
    Then the add execution api responds with status code of 500 and response of error message
