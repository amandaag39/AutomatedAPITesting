package com.ecommerce.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class TestClassForDependencies {
    private static final String BASE_URL = "https://dummyjson.com";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetUser() {
        Response response = given()
                .when()
                .get("/users/1")
                .then()
                .extract()
                .response();

        // Perform asserstion on the repsonse
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("firstName"), "Terry");
    }
}
