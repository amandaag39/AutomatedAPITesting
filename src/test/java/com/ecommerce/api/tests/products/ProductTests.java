package com.ecommerce.api.tests.products;

import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.PayloadFromFile;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

public class ProductTests {
	private static int total;
	private static int randomId;
	private static String keyword;
	private static int limit;
	private static int skip;

	static {
		total = ProductTestConstants.TOTAL;
		randomId = (int) (Math.random() * total) + 1;
		keyword = ProductTestConstants.KEYWORD;
		limit = ProductTestConstants.LIMIT;
		skip = ProductTestConstants.SKIP;
	}

	@Test
	public void createProductTest() {
		String uri = URICreator.getBaseURI("products/add");
		String payload = PayloadFromFile.generatePayload("product");
		Response response = BaseRequests.postRequest(uri, payload)
				.then()
				.assertThat()
				.statusCode(200)
				.body(containsString(payload.substring(1)))
				.extract().response();

		LogService.logData(payload, response);
	}


	@Test
	public void getAllProductsTest() {
		String uri = URICreator.getBaseURI("products");
		BaseRequests.getAllRequest(uri, total)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("products.size()", equalTo(total));
	}

	@Test
	public void getAllProductsPaginationTest() {
		String uri = URICreator.getBaseURI("products");
		BaseRequests.getAllWithPaginationRequest(uri, limit, skip)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("products.size()", equalTo(limit))
				.body("skip", equalTo(skip))
				.body("products[0].id", equalTo(skip + 1));
	}

	@Test
	public void getProductByIdTest() {
		String uri = URICreator.getBaseURI("products/" + randomId);
		Response response = BaseRequests.getByIdRequest(uri)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("id", equalTo(randomId))
				.body("title", Matchers.notNullValue())
				.body("description", Matchers.notNullValue())
				.body("price", Matchers.notNullValue())
				.extract().response();

		LogService.logData(response);
	}

	@Test
	public void getProductByKeywordTest() {
		String uri = URICreator.getBaseURI("products/search");
		Response response = BaseRequests.getByKeyword(uri, keyword)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(Matchers.containsString(keyword))
				.body("products.size()", greaterThan(0))
				.extract().response();

		LogService.logData(response);
	}

	@Test
	public void updateProductWithPutTest() {
		String uri = URICreator.getBaseURI("products/" + randomId);
		String payload = PayloadFromFile.generatePayload("updateProduct");

		Response response = BaseRequests.putRequest(uri, payload)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void updateProductWithPatch() {
		String uri = URICreator.getBaseURI("products/" + randomId);
		String payload = PayloadFromFile.generatePayload("updateProduct");

		Response response = BaseRequests.patchRequest(uri, payload)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();

		LogService.logData(payload, response);
	}

}
