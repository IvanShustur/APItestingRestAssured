package utils;

import models.Product;

import java.util.ArrayList;
import java.util.List;

public class HelpingMethodsForStreams {
    private HelpingMethodsForStreams() {
    }

    public static Product getProductById(List<Product> list, double id) {
        for (Product object : list) {
            if (object.getId() == id) {
                return object;
            }
        }
        return null;
    }

    public static List<Long> calculatingAveragePriceOfProducts(List<Product> list){
        List <Long> priceList = new ArrayList<>();
        for (Product object:list){
            priceList.add((long) object.getPrice());
        }
        return priceList;
    }
}