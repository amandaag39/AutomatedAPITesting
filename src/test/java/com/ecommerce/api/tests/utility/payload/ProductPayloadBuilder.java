package com.ecommerce.api.tests.utility.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import pojo.Product;

public class ProductPayloadBuilder {
    private static Product product;


    public static String generatePayload() {
        Faker faker = new Faker();
        Product product = Product.builder()
                .title(faker.commerce().productName())
                .description(faker.lorem().sentence())
                .price(faker.number().randomDouble(2,1,1000))
                .discountPercentage(faker.number().randomDouble(2, 0, 50))
                .rating(faker.number().randomDouble(2,1,5))
                .stock(faker.number().randomDigit())
                .brand(faker.company().name())
                .category(faker.commerce().department())
                .thumbnail(faker.internet().url())
                .images(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String productJSON = objectMapper.writeValueAsString(product);
            return productJSON;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String generatePartialPayload() {
        Faker faker = new Faker();
        Product product = Product.builder()
                .title(faker.commerce().productName())
                .description(faker.lorem().sentence())
                .price(faker.number().randomDouble(2,1,1000))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String productJSON = objectMapper.writeValueAsString(product);
            return productJSON;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

}
