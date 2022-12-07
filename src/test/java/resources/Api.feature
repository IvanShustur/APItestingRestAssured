Feature: CRUD of product

  Scenario: User creation verification
    Given the user make post call to server
    Then the user check that the product is created

   Scenario: User updating verification
    Given the user make put call to server
    Then  the user check that the product is updated

   Scenario: User getting verification
     Given the user make get call to server
     Then  the user check that the product meets expectations

   Scenario: User deleting verification
     Given the user make delete call to server
     Then  the user check that the product was deleted