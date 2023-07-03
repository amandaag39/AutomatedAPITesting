package com.ecommerce.api.tests.users;

import com.ecommerce.api.tests.utility.BaseRequests;
import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.PayloadFromFile;
import com.ecommerce.api.tests.utility.payload.UserPayloadGenerator;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;


public class UserTests {
	private static int total;
	private static int randomId;

	static {
		total = UserTestConstants.TOTAL;
		randomId = (int) (Math.random() * total) + 1;
	}

	@Test
	public void getAllUsersTest() {
		String uri = URICreator.getBaseURI("users");
		BaseRequests.getAllRequest(uri, total)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON).and()
				.body("users.size()", equalTo(total));
	}

	@Test
	public void getUserByIdTest() {
		String uri = URICreator.getBaseURI("users/" + randomId);
		Response response = BaseRequests.getByIdRequest(uri)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();

		LogService.logData(response);
	}

	@Test
	public void createUserTest() {
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
	public void createRandomUserTest() {
		String uri = URICreator.getBaseURI("users/add");
		String payload = UserPayloadGenerator.generatePayload();
		Response response = BaseRequests.postRequest(uri, payload)
				.then()
				.assertThat()
				.statusCode(200).and()
				.contentType(ContentType.JSON).and()
				.body(containsString(payload.substring(1, payload.length()-1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void updateUserWithPutTest() {
		String uri = URICreator.getBaseURI("users/" + randomId);
		String payload = PayloadFromFile.generatePayload("user");
		Response response = BaseRequests.putRequest(uri, payload)
				.then().assertThat()
				.statusCode(200).and()
				.contentType(ContentType.JSON).and()
				.body(containsString(payload.substring(1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void updateUserWithPatchTest() {
		String uri = URICreator.getBaseURI("users/" + randomId);
		String payload = UserPayloadGenerator.generatePayload();
		Response response = BaseRequests.patchRequest(uri, payload)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(containsString(payload.substring(1, payload.length()-1)))
				.extract().response();

		LogService.logData(payload, response);
	}

	@Test
	public void deleteUserTest() {
		String uri = URICreator.getBaseURI("users/" + randomId);
		BaseRequests.deleteRequest(uri)
				.then().assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("isDeleted", Matchers.equalTo(true));
	}

	@Test
	public void validateGetUserSchema() {
		String filePath = System.getProperty("user.dir") + "/src/test/resources/schemas/userschema.json";
		File userJsonSchema = new File(filePath);

		String uri = URICreator.getBaseURI("users/" + randomId);
		BaseRequests.getByIdRequest(uri)
				.then()
				.assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(userJsonSchema));
	}
}
