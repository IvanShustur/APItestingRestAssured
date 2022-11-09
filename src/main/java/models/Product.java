package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Product {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private double price;
    @JsonProperty("category_id")
    private int categoryId;
    @JsonProperty("category_name")
    private String categoryName;



    //Used for Post requests
    public Product (String name, String description, double price, int categoryId){
        setName(name);
        setDescription(description);
        setPrice(price);
        setCategoryId(categoryId);
    }
}

