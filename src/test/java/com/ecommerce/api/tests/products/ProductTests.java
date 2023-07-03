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

	static {
		total = ProductTestConstants.TOTAL;
		randomId = (int) (Math.random() * total) + 1;
		keyword = "laptop";
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
