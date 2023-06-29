package com.ecommerce.api.tests.users;

import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.PayloadFromFile;
import com.ecommerce.api.tests.utility.payload.UserPayloadGenerator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class UserPositive {
	private static int limit;
	private static int randomId;

	static {
		limit = UserTestConstants.LIMIT;
		randomId = (int) (Math.random() * limit) + 1;
	}

	@Test
	public void getAllUsers() {
		String uri = URICreator.getBaseURI("users");
		BaseRequests.getAllRequest(uri, limit)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("users.size()", equalTo(limit));
	}

	@Test
	public void getUserById() {
		String uri = URICreator.getBaseURI("users/" + randomId);
		Response response = BaseRequests.getByIdRequest(uri)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("id", equalTo(randomId))
				.body("id", notNullValue())
				.body("firstName", notNullValue())
				.body("lastName", notNullValue())
				.body("email", notNullValue())
				.extract().response();

		LogService.logData(response);

	}

	@Test
	public void createUser() {

		String uri = URICreator.getBaseURI("users/add");
		String payload = PayloadFromFile.generatePayload("user");
		Response response = BaseRequests.postRequest(uri, payload)
				.then()
				.assertThat()
				.statusCode(200)
				.body(containsString(payload.substring(1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void createRandomUser() {
		Logger logger = LogManager.getLogger();
		String uri = URICreator.getBaseURI("users/add");
		String payload = UserPayloadGenerator.generatePayload();
		Response response = BaseRequests.postRequest(uri, payload)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(containsString(payload.substring(1,
								payload.length()-1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void updateUserWithPut() {
		Logger logger = LogManager.getLogger();

		String uri = URICreator.getBaseURI("users/" + randomId);
		String payload = PayloadFromFile.generatePayload("user");
		Response response = BaseRequests.putRequest(uri, payload)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(containsString(payload.substring(1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void updateUserWithPatch() {
		Logger logger = LogManager.getLogger();

		String uri = URICreator.getBaseURI("users/" + randomId);
		String payload = UserPayloadGenerator.generatePayload();
		Response response = BaseRequests.putRequest(uri, payload)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(containsString(payload.substring(1, payload.length()-1)))
				.extract().response();

		LogService.logData(payload, response);
	}
}
