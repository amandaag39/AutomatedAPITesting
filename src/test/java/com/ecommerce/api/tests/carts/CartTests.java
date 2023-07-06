package com.ecommerce.api.tests.carts;

import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.PayloadFromFile;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

public class CartTests {

    private static int total;
    private static int randomId;
    private static int limit;

    static {
        total = CartTestConstants.TOTAL;
        randomId = (int) (Math.random() * total) + 1;
        limit = CartTestConstants.LIMIT;
    }

    @Test
    public void createCartTest() {

        String uri = URICreator.getBaseURI("carts/add");
        String payload = PayloadFromFile.generatePayload("cart");
        JSONObject payloadJson = new JSONObject(payload);

        Response response = BaseRequests.postRequest(uri, payload)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(limit + 1))
                .body("userId", equalTo(payloadJson.getInt("userId")))
                .extract().response();

        LogService.logData(payload, response);
    }

    @Test
    public void getAllCartsTest() {
        String uri = URICreator.getBaseURI("carts/");

        BaseRequests.getAllRequest(uri, total)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON).and()
                .body("carts.size()", equalTo(total));
    }

    @Test
    public void getSingleCartTest(){
        String uri = URICreator.getBaseURI("carts/" + randomId);
        Response response = BaseRequests.getByIdRequest(uri)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        LogService.logData(response);
    }

    @Test
    public void getCartOfUser(){
        int userId = 5;
        String uri = URICreator.getBaseURI("carts/user/" + userId);
        Response response = BaseRequests.getByIdRequest(uri)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("carts.products[0].title", hasItem("Rose Ring"))
                .extract().response();

        LogService.logData(response);
    }

    @Test
    public void updateCart(){

        String uri = URICreator.getBaseURI("carts/" + randomId);
        String payload = PayloadFromFile.generatePayload("updateCart");

        Response response = BaseRequests.putRequest(uri, payload)
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        String responseToString = response.asString();

        JsonPath js = new JsonPath(responseToString);
        int count = js.getInt("products.size()");

        int sum = 0;
        for(int i=0; i<count; i++){
            int price = js.getInt("products["+i+"].price");
            int quantity = js.getInt("products["+i+"].quantity");
            int totalAmountPerProduct = price * quantity;
            sum = sum + totalAmountPerProduct;
        }

        int purchasedAmount = js.getInt("total");
        Assert.assertEquals(sum, purchasedAmount);

        LogService.logData(payload, response);
    }

    @Test
    public void deleteCart(){
        String uri = URICreator.getBaseURI("carts/" + randomId);
        BaseRequests.deleteRequest(uri)
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isDeleted", equalTo(true));
    }
}
