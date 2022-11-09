package trainingXyz;


import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiTestsWithLombok {

    Product expectedProduct = Product.builder().id(2).
            name("Cross-Back Training Tank").
            description("The most awesome phone of 2013!").
            price(299.00).
            categoryId(2).
            categoryName("Active Wear - Women").build();

    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product actualProduct = given().
                queryParam("id", "2").
                when().
                get(endpoint).
                as(Product.class);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void getProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product responseProduct = given().
                queryParam("id", 2).
                when().get(endpoint).as(Product.class);
        assertThat(responseProduct.getId()).isEqualTo(expectedProduct.getId());
        assertThat(responseProduct).hasSameClassAs(expectedProduct);
        assertThat(responseProduct.getPrice()).isEqualTo(expectedProduct.getPrice());
    }
        @Test
        public void createProduct() {
            String endpoint = "http://localhost:80/api_testing/product/create.php";
            Product postProduct = new Product("Water Bottle",
                    "Blue water bottle. Holds 64 ounces",
                    12,
                    3);
            var response = given().body(postProduct).when().post(endpoint).then();
            assertThat(postProduct.getCategoryId()).isNotNull();
            response.log().body();
        }

        @Test
        public void updateProduct () {
            String endpoint = "http://localhost:80/api_testing/product/update.php";
            Product productForUpdate = new Product(
                    "Water Bottle",
                    "Blue water bottle. Holds 64 ounces",
                    15,
                    3);
            var response = given().body(productForUpdate).when().put(endpoint).then();
            response.log().body();
        }

        @Test
        public void deleteProduct() {
            String endpoint = "http://localhost:80/api_testing/product/delete.php";
            String body = "{ \"id : \"  19}";
            var response = given().body(body).when().delete(endpoint).then();
            response.log().body();
        }

        @Test
        public void createSerializedProduct () {
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
        public void createSweatband () {
            String endpoint = "http://localhost:80/api_testing/product/create.php";
            Product sweatBand = new Product("Sweatband",
                    "The sweatband is black",
                    15,
                    3);
            var response = given().body(sweatBand).when().post(endpoint).then();
            response.log().body();
        }

        @Test
        public void updateSweatband(){
            String endpoint = "http://localhost:80/api_testing/product/update.php";
            String body = "{ + \n" +
                    "\"id\": 26, + \n" +
                     "\"price\": 10 \n"+
                    '}';
            var response = given().body(body).when().put(endpoint).then();
            response.log().body();
        }

        @Test
        public void getSweatband(){
            String endpoint = "http://localhost:80/api_testing/product/read_one.php";
            var response =
                    given().
                            queryParam("id", 26).
                            when().get(endpoint).
                            then();
            response.log().body();
        }

        @Test
        public void deleteSweatband(){
            String endpoint = "http://localhost:80/api_testing/product/delete.php";
            String body = "{ \"id :  \"26 }";
            var response = given().body(body).when().delete(endpoint).then();
            response.log().body();
        }

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
            assertThat(multivitamin.equals(expectedMultivitaminProduct));

        }
}


