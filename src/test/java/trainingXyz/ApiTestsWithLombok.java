package trainingXyz;


import io.restassured.http.ContentType;
import models.Product;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileReader;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ApiTestsWithLombok {

    Product expectedProduct = Product.builder().id(2).
            name("Cross-Back Training Tank").
            description("The most awesome phone of 2013!").
            price(299.00).
            categoryId(3).
            categoryName("Active Wear - Women").build();

    SoftAssertions softAssertions = new SoftAssertions();

    @DisplayName("Using deserializing of product")
    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product actualProduct = given().
                queryParam("id", "2").
                when().
                get(endpoint).
                as(Product.class);
        assertThat(actualProduct).withFailMessage("Expected product do not equals to actual").isEqualTo(expectedProduct);
    }

    @DisplayName("Validating response using softAsserts")
    @Test
    public void getProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product responseProduct = given().
                queryParam("id", 2).
                when().get(endpoint).as(Product.class);
        softAssertions.assertThat(responseProduct.getId()).isEqualTo(expectedProduct.getId());
        softAssertions.assertThat(responseProduct.getPrice()).isEqualTo(expectedProduct.getPrice());
        softAssertions.assertThat(responseProduct.getCategoryId()).isEqualTo(expectedProduct.getCategoryId());
        softAssertions.assertThat(responseProduct.getName()).isEqualTo(expectedProduct.getName());
        softAssertions.assertAll();
    }

    @DisplayName("This test creating new product and verifying if categoryId is not null")
    @Test
    public void createProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product postProduct = new Product("Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                12,
                3);
        var response = given().body(postProduct).when().post(endpoint).then();
        response.log().body();
        assertThat(postProduct.getCategoryId()).withFailMessage("CategoryId is null").isNotNull();
    }

    @DisplayName("Verifying updating the product with adding to assert fail message")
    @Test
    public void updateProduct() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        Product productForUpdate = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                15,
                3);
        var response = given().body(productForUpdate).when().put(endpoint).then();
        assertThat(productForUpdate.getPrice()).withFailMessage("The price should be <%s> but was <%s>",
                        productForUpdate.getPrice(), 100).
                isEqualTo(100);
        response.log().body();

    }

    @DisplayName("Verifying deleting a product")
    @Test
    public void deleteProduct() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{ \"id : \"  19}";
        var response = given().body(body).when().delete(endpoint).then().
                assertThat().statusCode(HttpStatus.SC_OK).
                body("message", equalTo("Product was deleted."));
        response.log().body();


    }

    @DisplayName("Creating a product then verifying status code and message from body")
    @Test
    public void createSerializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds  ounces",
                12,
                3
        );
        var response = given().body(product).when().post(endpoint).then().
                assertThat().
                   statusCode(HttpStatus.SC_CREATED).
                body("message", equalTo("Product was created."));

    }

    @DisplayName("Creating a product from the task with verifying status code and message from response")
    @Test
    public void createSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product sweatBand = new Product("Sweatband",
                "The sweatband is black",
                15,
                3);
        var response = given().body(sweatBand).when().post(endpoint).then().
                assertThat().statusCode(HttpStatus.SC_CREATED).
                body("message", equalTo("Product was created."));
        //Product receivedProduct = given().body(sweatBand).when().post(endpoint).as(Product.class);
        response.log().body();

    }

    @DisplayName("Updating a product from the task with verifying status code and message from response")
    @Test
    public void updateSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = "{ + \n" +
                "\"id\": 26, + \n" +
                "\"price\": 10 \n" +
                '}';
        var response = given().body(body).when().put(endpoint).then().
                assertThat().statusCode(HttpStatus.SC_OK).
                body("message", equalTo("Product updated"));
        response.log().body();
    }

    @DisplayName("Getting a product which does not exist")
    @Test
    public void getSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 26).
                        when().get(endpoint).
                        then().
                        assertThat().statusCode(HttpStatus.SC_NOT_FOUND).
                        body("message", equalTo("Product does not exist."));
        response.log().body();
    }

    @DisplayName("Deleting a product")
    @Test
    public void deleteSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{ \"id :  \"26 }";
        var response = given().body(body).when().delete(endpoint).then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
        response.log().body();
    }

    @DisplayName("Getting a product from server and verifying his name with the expected")
    @Test
    public void getMultivitaminProduct() {
        Product expectedMultivitaminProduct = new Product(18,
                "Multi-Vitamin (90 capsules)",
                "A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.",
                10.00,
                4,
                "Supplements"
        );

        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product multivitamin = given().
                queryParam("id", 18).
                when().
                get(endpoint).as(Product.class);
        assertThat(multivitamin.getName()).withFailMessage("The names do not match").isEqualTo(expectedMultivitaminProduct.getName());


    }


}


