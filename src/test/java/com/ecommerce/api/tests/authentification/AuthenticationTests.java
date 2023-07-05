package com.ecommerce.api.tests.authentification;

import com.ecommerce.api.tests.utility.LogService;
import com.ecommerce.api.tests.utility.URICreator;
import com.ecommerce.api.tests.utility.payload.AuthDataBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationTests {
	private static String token;

	@Test(priority = 1)
    public static void getTokenValidCredsTest(){
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

		System.out.println(token);
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

	@Test
	public static void getTokenInvalidCredsTest(){}

	@Test
	public static void getTokenEmptyPayloadTest(){}

}
