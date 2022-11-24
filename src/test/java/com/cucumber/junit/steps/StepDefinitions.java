package com.cucumber.junit.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.Product;
import org.apache.http.HttpStatus;
import org.openqa.selenium.devtools.v85.fetch.model.AuthChallengeResponse;
import utils.FileReader;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {

    Response responseFromCreating;
    Product productFromReading;
    Response responseFromDeleting;
    Response responseFromUpdating;

    @Given("the user make post call to server")
    public Response theUserMakePostCallToServer() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        responseFromCreating = given().
                body(FileReader.readObject("src/main/resources/Products/validProduct.json",Product.class)).
                  when().
                   post(endpoint);
        return responseFromCreating;
    }

    @Then("the user check that the product is created")
    public void theUserCheckThatTheProductIsCreated() {
        assertThat(responseFromCreating.getStatusCode()).
                withFailMessage("Status codes do not match"). isEqualTo(HttpStatus.SC_CREATED);
    }



    @Given("the user make put call to server")
    public Response theUserMakePutCallToServer() {
         String endpoint = "http://localhost:80/api_testing/product/update.php";
         responseFromUpdating = given().
                 body(FileReader.readObject("src/main/resources/Products/updatesProduct.json",Product.class)).
                   when().
                    put(endpoint);
         return responseFromUpdating;

    }

    @Then("the user check that the product is updated")
    public void theUserCheckThatTheProductIsUpdated() {
        assertThat(responseFromUpdating.getStatusCode()).as("Status codes do not match").isEqualTo(HttpStatus.SC_OK);
    }



    @Given("the user make get call to server")
    public Product theUserMakeGetCallToServer() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        productFromReading = given().
                queryParam("id", 17).
                  when().
                    get(endpoint).as(Product.class);
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
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{ \"id : \"  555}";
        responseFromDeleting = given().
                 body(body).
                   when().
                     delete(endpoint);
    }
    @Then("the user check that the product was deleted")
    public void theUserCheckThatProductWasDeleted(){
        assertThat(responseFromDeleting.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}