package com.cucumber.junit.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;
import models.Product;
import models.RequestBody;
import org.apache.http.HttpStatus;
import utils.FileReader;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {

    Response responseFromCreating;
    Product productFromReading;
    Response responseFromDeleting;
    Response responseFromUpdating;
    String endpointForCreating = "http://localhost:80/api_testing/product/create.php";
    String endpointForUpdating = "http://localhost:80/api_testing/product/update.php";
    String endpointForGetting = "http://localhost:80/api_testing/product/read_one.php";
    String endpointForDeleting = "http://localhost:80/api_testing/product/delete.php";

    @Given("the user make post call to server")
    public Response theUserMakePostCallToServer() {
        responseFromCreating = given().
                body(FileReader.readObject("src/main/resources/Products/validProduct.json",Product.class)).
                  when().
                   post(endpointForCreating);
        return responseFromCreating;
    }

    @Then("the user check that the product is created")
    public void theUserCheckThatTheProductIsCreated() {
        assertThat(responseFromCreating.getStatusCode()).
                withFailMessage("Status codes do not match"). isEqualTo(HttpStatus.SC_CREATED);
    }



    @Given("the user make put call to server")
    public Response theUserMakePutCallToServer() {
        Product body = new Product(1100,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women");
         responseFromUpdating = given().
                 body(body).
                   when().
                    put(endpointForUpdating);
         return responseFromUpdating;

    }

    @Then("the user check that the product is updated")
    public void theUserCheckThatTheProductIsUpdated() {
        assertThat(responseFromUpdating.getStatusCode()).as("Status codes do not match").isEqualTo(HttpStatus.SC_OK);
    }



    @Given("the user make get call to server")
    public Product theUserMakeGetCallToServer() {
        RequestBody body = new RequestBody(17);
        productFromReading = given().
                body(body).
                 when().
                  get(endpointForGetting).as(Product.class);
        System.out.println("Reading : " + productFromReading);
        return productFromReading;
    }

    @Then("the user check that the product meets expectations")
    public void theUserCheckThatTheProductMeetsExpectations() {
        assertThat(productFromReading).
                as("Products do not equals").
                isEqualTo(FileReader.readObject("src/main/resources/Products/expectedProduct.json",Product.class));
    }



    @Given("the user make delete call to server")
    public void theUserMakeDeleteCallToServer() {
        RequestBody body = new RequestBody(555);
        responseFromDeleting = given().
                 body(body).
                   when().
                     delete(endpointForDeleting);
    }

    @Then("the user check that the product was deleted")
    public void theUserCheckThatProductWasDeleted(){
        assertThat(responseFromDeleting.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}