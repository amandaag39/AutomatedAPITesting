package com.ecommerce.api.tests.products;

import com.ecommerce.api.tests.users.UserTestConstants;
import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.URICreator;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class ProductTests {
	private static int limit;
	private static int randomId;

	static {
		limit = ProductTestConstants.LIMIT;
		randomId = (int) (Math.random() * limit) + 1;
	}
	@Test
	public void getAllProducts() {
		String uri = URICreator.getBaseURI("products");
		BaseRequests.getAllRequest(uri, limit)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("products.size()", equalTo(limit));
	}
}
