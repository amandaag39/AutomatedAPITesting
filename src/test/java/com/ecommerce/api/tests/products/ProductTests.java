package com.ecommerce.api.tests.products;

import com.ecommerce.api.tests.users.UserTestConstants;
import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
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
		keyword = "laptop";
		limit = ProductTestConstants.LIMIT;
		skip = ProductTestConstants.SKIP;
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
				.body(Matchers.containsString("laptop"))
				.body("products.size()", greaterThan(0))
				.extract().response();

		LogService.logData(response);
	}
}
