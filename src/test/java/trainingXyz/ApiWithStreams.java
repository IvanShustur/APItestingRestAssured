package trainingXyz;

import models.Product;
import models.Records;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HelpingMethodsForStreams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiWithStreams {
    String endpoint = "http://localhost:80/api_testing/product/read.php";

    @DisplayName("Check does products in the category \n" +
            "\"Active wear - women\" contains product with a name \"Stretchy Dance Pants\"")
    @Test
    void verifyExistingProductWithExactName() {
        var responseProducts= given().
                when().
                get(endpoint);
        Records records = responseProducts.getBody().as(Records.class);
        List<Product> productsList  = Arrays.asList(records.getRecords());
        List<Product> categoryActiveWearWomenList = productsList.stream().
                filter(c -> c.getCategoryName().equals( "Active Wear - Women")).collect(Collectors.toList());
        List<Product> nameFromCategoryActiveWearWomen = categoryActiveWearWomenList.
                stream().filter(c -> c.getName().equals("Stretchy Dance Pants")).collect(Collectors.toList());
       assertThat(nameFromCategoryActiveWearWomen).as("Category \"Active wear - women\" contains product with a name \"Stretchy Dance Pants\"").
               isNotNull();
    }

    @DisplayName("Check does product with id = 3 has price = 68.0")
    @Test
    void checkThePriceOfProductWithExactId(){
        double comparingPrice = 68.00;
        var responseProducts= given().
                when().
                get(endpoint);
        Records records = responseProducts.getBody().as(Records.class);
        List<Product> productsList  = Arrays.asList(records.getRecords());
        Product actualProduct = HelpingMethodsForStreams.getProductById(productsList,3);
        assert actualProduct != null;
        assertThat(actualProduct.getPrice()).
                as("The price of the product is not as expected").
                    isEqualTo(comparingPrice);
    }

    @DisplayName("Check if does a product with id = 1 has a price greater than 90.0")
    @Test
    void comparingThePriceOfProductWithExactId(){
        double comparingPrice = 90.00;
        var responseProducts= given().
                when().
                get(endpoint);
        Records records = responseProducts.getBody().as(Records.class);
        List<Product> productsList  = Arrays.asList(records.getRecords());
        Product actualProduct = HelpingMethodsForStreams.getProductById(productsList,1);
        assert actualProduct != null;
        assertThat(actualProduct.getPrice()).withFailMessage("The price of first product <%s> is not bigger \n" +
                        " than the price of second product <%s>",
                actualProduct.getPrice(),comparingPrice).
                                        isGreaterThan(comparingPrice);
    }

    @DisplayName("Calculate the average price for products with help of stream")
    @Test
    void averagePrice (){
        var responseProducts= given().
                when().
                get(endpoint);
        Records records = responseProducts.getBody().as(Records.class);
        List<Product> productsList  = Arrays.asList(records.getRecords());
        List<Long> priceOfProducts =HelpingMethodsForStreams.calculatingAveragePriceOfProducts(productsList);
        OptionalDouble averagePrice = priceOfProducts.stream().mapToLong(o -> o).average();
        assertThat(averagePrice).as("Average price is Null").isNotNull();
    }

    @DisplayName("Create a map with ids as keys and names as a value")
    @Test
    void addingToMapProductFields(){
        String nameOfProduct = "Cross-Back Training Tank";
        var responseProducts= given().
                when().
                get(endpoint);
        Records records = responseProducts.getBody().as(Records.class);
        List<Product> productsList  = Arrays.asList(records.getRecords());
        Map<Integer, String> createdMap= productsList.stream().collect(Collectors.toMap(Product::getId,Product::getName));
        assertThat(createdMap).as("Map does not contain value").containsValue(nameOfProduct);
    }
}