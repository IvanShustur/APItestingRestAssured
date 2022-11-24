Feature: As a user I want to test API

  Scenario: As a user I create new product
    Given the user make post call to server
    Then the user check that the product is created

   Scenario: As a user I update the product
    Given the user make put call to server
    Then  the user check that the product is updated

   Scenario: As a user I read the product
     Given the user make get call to server
     Then  the user check that the product meets expectations

   Scenario: As a user I delete the product
     Given the user make delete call to server
     Then  the user check that the product was deleted