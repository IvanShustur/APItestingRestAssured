package trainingXyz;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {
    @Test
    public void getCategories() {
        String endpoint = "http://localhost:80/api_testing/category/read.php";
        var responseCategoriesList = given().when().get(endpoint).then();
        responseCategoriesList.log().body();
        System.out.println(responseCategoriesList);
    }

    @Test
    void getProducts() {
        String endpoint = "http://localhost:80/api_testing/product/read.php";
        var responseProducts = given().
                  when().
                get(endpoint).
                  then().
                log().
                 body().
                assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json; charset=UTF-8")).
                body("records.size()", greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.description", everyItem(notNullValue())).
                body("records.price", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.category_name", everyItem(notNullValue())).
                body("records.id[0]", equalTo("1011"));
    }

    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );
        Product actualProduct = given().
                queryParam("id", "2").
                when().
                get(endpoint).
                as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }

    @Test
    public void getProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        given().
                queryParam("id", 2).
                when().get(endpoint).
                then().log().body().
                assertThat().
                statusCode(200).body("id", equalTo("2")).
                body("name", equalTo("Cross-Back Training Tank")).
                body("description", equalTo("The most awesome phone of 2013!")).
                body("price", equalTo("299.00")).
                body("category_id", equalTo("2")).
                body("category_name", equalTo("Active Wear - Women"));
    }

    @Test
    public void createProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = "{" + "\n" +
                '"' + "name" + '"' + ": " + '"' + "Water Bottle" + '"' + ',' + "\n" +
                '"' + "description" + '"' + ": " + '"' + "Blue water bottle. Holds 64 ounces" + '"' + ',' + "\n" +
                '"' + "price" + '"' + ": " + 12 + "," + "\n" +
                '"' + "category_id" + '"' + ": " + 3 + "\n" +
                '}';
        var response = given().body(body).when().post(endpoint).then();
        System.out.println(body);
        response.log().body();
    }

    @Test
    public void updateProduct() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = "{" + "\n" +
                "id" + ": " + 19 + ',' + "\n" +
                "name" + ": " + "Water Bottle" + ',' + "\n" +
                "description" + ": " + "Blue water bottle. Holds 64 ounces" + ',' + "\n" +
                "price" + ": " + 15 + ',' + "\n" +
                "category_id" + ": " + 3 + "\n" +
                "}";
        var response = given().body(body).when().put(endpoint).then();
        System.out.println(body);
        response.log().body();
    }

    @Test
    public void deleteProduct() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{ \"id : \" + 19 + }";
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds  ounces",
                12,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = '{' + "\n" +
                '"' + "name" + '"' + ": " + '"' + "Sweatband" + '"' + ',' + "\n" +
                '"' + "description" + '"' + ": " + '"' + "The sweatband is black" + '"' + ',' + "\n" +
                '"' + "price" + '"' + ": " + 15 + ',' + "\n" +
                '"' + "category_id" + '"' + ": " + 3 + "\n" +
                '}';
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = '{' + "\n" +
                '"' + "id" + '"' + ": " + 26 + ',' + "\n" +
                '"' + "price" + '"' + ": " + 10 + "\n" +
                '}';
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void getSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 26).
                        when().get(endpoint).
                        then();
        response.log().body();
    }

    @Test
    public void deleteSweatband() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{" +
                "id : " + 26 +
                "}";
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void getMultivitaminProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        given().
                queryParam("id", 18).
                when().
                    get(endpoint).
                then().
                     assertThat().statusCode(200).
                          header("Content-Type", equalTo("application/json")).
                          body("id", equalTo("18")).
                          body("name", equalTo("Multi-Vitamin (90 capsules)")).
                          body("description", equalTo("A daily dose of our Multi-Vitamins fulfills a day’s "+
                                  "nutritional needs for over 12 vitamins and minerals.")).
                          body("price", equalTo("10.00")).
                          body("category_id", equalTo("4")).
                          body("category_name", equalTo("Supplements"));
    }

}
