package com.ecommerce.api.tests.authentification;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.AuthDataBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationTests {
	private static String token;

	@Test(priority = 1)
    public static void getTokenValidCredsTest() {
		String uri = URICreator.getBaseURI("auth/login");
		token = given()
				.body(AuthDataBuilder.validAuthData())
				.contentType(ContentType.JSON)
				.when()
				.post(uri)
				.then()
				.assertThat()
				.statusCode(200)
				.time(lessThan(2000L))
				.body("token", is(notNullValue()))
				.extract()
				.path("token").toString();
	}

	@Test(priority = 2, dependsOnMethods = "getTokenValidCredsTest")
	public static void getResourceWithToken()  {
		String uri = URICreator.getBaseURI("auth/carts");
		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.when()
				.get(uri)
				.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.body("carts", Matchers.notNullValue());
	}

	@Test(priority = 2, dependsOnMethods = "getTokenValidCredsTest")
	public static void getResourceWithEmptyToken()  {
		String uri = URICreator.getBaseURI("auth/carts");
		given()
				.header("Authorization", "")
				.contentType(ContentType.JSON)
				.when()
				.get(uri)
				.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(403)
				.body("message", equalToIgnoringCase("Authentication Problem"));
	}

	@Test
	public static void getTokenInvalidCredsTest(){
		String uri = URICreator.getBaseURI("auth/login");
		given()
				.body(AuthDataBuilder.invalidAuthData())
				.contentType(ContentType.JSON)
				.when()
				.post(uri)
				.then()
				.assertThat()
				.statusCode(400)
				.body("message", containsString("Invalid credentials"));
	}

	@Test
	public static void getTokenEmptyPayloadTest(){	String uri = URICreator.getBaseURI("auth/login");
		given()
				.body(AuthDataBuilder.emptyPayloadAuthData())
				.contentType(ContentType.JSON)
				.when()
				.post(uri)
				.then()
				.assertThat()
				.statusCode(400)
				.body("message", containsString("Invalid credentials"));}
}
