package trainingXyz;

import models.Product;
import models.RequestBody;
import models.RequestBodyForUpdate;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    String endpointForGetRequest = "http://localhost:80/api_testing/product/read_one.php";
    String endpointForCreateRequest = "http://localhost:80/api_testing/product/create.php";
    String endpointForUpdateRequest = "http://localhost:80/api_testing/product/update.php";
    String endpointForDeleteRequest = "http://localhost:80/api_testing/product/delete.php";

    @DisplayName("Using deserializing of product")
    @Test
    public void getDeserializedProduct() {
        RequestBody requestBody = new RequestBody(2);
        Product actualProduct = given().
                body(requestBody).
                when().
                get(endpointForGetRequest).
                as(Product.class);
        assertThat(actualProduct).withFailMessage("Expected product do not equals to actual").isEqualTo(expectedProduct);
    }

    @DisplayName("Validating response using softAsserts")
    @Test
    public void getProduct() {
        RequestBody requestBody = new RequestBody(2);
        Product responseProduct = given().
                body(requestBody).
                when().get(endpointForGetRequest).as(Product.class);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(responseProduct.getId()).isEqualTo(expectedProduct.getId());
        softAssertions.assertThat(responseProduct.getPrice()).isEqualTo(expectedProduct.getPrice());
        softAssertions.assertThat(responseProduct.getCategoryId()).isEqualTo(expectedProduct.getCategoryId());
        softAssertions.assertThat(responseProduct.getName()).isEqualTo(expectedProduct.getName());
        softAssertions.assertAll();
    }

    @DisplayName("This test creating new product and verifying if categoryId is not null")
    @Test
    public void createProduct() {
        Product postProduct = new Product("Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                12,
                3);
        var response = given().body(postProduct).when().post(endpointForCreateRequest).then();
        response.log().body();
        assertThat(postProduct.getCategoryId()).withFailMessage("CategoryId is null").isNotNull();
    }

    @DisplayName("Verifying updating the product with adding to assert fail message")
    @Test
    public void updateProduct() {
        Product productForUpdate = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                15,
                3);
        var response = given().body(productForUpdate).when().put(endpointForUpdateRequest).then();
        assertThat(productForUpdate.getPrice()).withFailMessage("The price should be <%s> but was <%s>",
                        productForUpdate.getPrice(), 100).
                isEqualTo(100);
        response.log().body();

    }

    @DisplayName("Verifying deleting a product")
    @Test
    public void deleteProduct() {
        Product body = new Product();
        body.setId(26);
        var response = given().body(body).when().delete(endpointForDeleteRequest).then().
                assertThat().statusCode(HttpStatus.SC_OK).
                body("message", equalTo("Product was deleted."));
        response.log().body();
    }

    @DisplayName("Creating a product then verifying status code and message from body")
    @Test
    public void createSerializedProduct() {
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds  ounces",
                12,
                3);
        var response = given().body(product).when().post(endpointForCreateRequest).then().
                assertThat().
                statusCode(HttpStatus.SC_CREATED).
                body("message", equalTo("Product was created."));
    }

    @DisplayName("Creating a product from the task with verifying status code and message from response")
    @Test
    public void createSweatband() {
        Product sweatBand = new Product("Sweatband",
                "The sweatband is black",
                15,
                3);
        var response = given().body(sweatBand).when().post(endpointForCreateRequest).then().
                assertThat().statusCode(HttpStatus.SC_CREATED).
                body("message", equalTo("Product was created."));
        response.log().body();

    }

    @DisplayName("Updating a product from the task with verifying status code and message from response")
    @Test
    public void updateSweatband() {
        RequestBodyForUpdate requestBodyForUpdate = new RequestBodyForUpdate(26, 10);
        var response = given().body(requestBodyForUpdate).when().put(endpointForUpdateRequest).then().
                assertThat().statusCode(HttpStatus.SC_OK).
                body("message", equalTo("Product updated"));
        response.log().body();
    }

    @DisplayName("Getting a product which does not exist")
    @Test
    public void getSweatband() {
        RequestBody requestBody = new RequestBody(26);
        var response =
                given().
                        body(requestBody).
                        when().get(endpointForGetRequest).
                        then().
                        assertThat().statusCode(HttpStatus.SC_NOT_FOUND).
                        body("message", equalTo("Product does not exist."));
        response.log().body();
    }

    @DisplayName("Deleting a product")
    @Test
    public void deleteSweatband() {
        RequestBody requestBody = new RequestBody(26);
        var response = given().body(requestBody).when().delete(endpointForDeleteRequest).then().
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

        Product multivitamin = given().
                queryParam("id", 18).
                when().
                get(endpointForGetRequest).as(Product.class);
        assertThat(multivitamin.getName()).withFailMessage("The names do not match").isEqualTo(expectedMultivitaminProduct.getName());
    }
}


