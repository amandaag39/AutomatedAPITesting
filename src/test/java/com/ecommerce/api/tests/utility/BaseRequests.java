package com.ecommerce.api.tests.utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseRequests {

	public static Response getAllRequest(String requestURI, int limit) {
		RequestSpecification requestSpecification = RestAssured.given()
				.param("limit", 0);
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response getAllWithPaginationRequest(String requestURI, int limit, int skip) {
		RequestSpecification requestSpecification = RestAssured.given()
				.param("limit", limit)
				.param("skip", skip);
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response getByIdRequest(String requestURI) {
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response getByKeyword(String requestURI, String keyword) {
		RequestSpecification requestSpecification = RestAssured.given()
				.param("q", keyword);
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response getFiltered(String requestURI, String key, String value) {
		RequestSpecification requestSpecification = RestAssured.given()
				.param("key", key)
				.param("value", value);
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response postRequest(String requestURI, String payload) {
		RequestSpecification requestSpecification = RestAssured.given()
				.body(payload);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.post(requestURI);
		return response;
	}

	public static Response putRequest(String requestURI, String payload) {
		RequestSpecification requestSpecification = RestAssured.given()
				.body(payload);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.put(requestURI);
		return response;
	}

	public static Response patchRequest(String requestURI, String payload) {
		RequestSpecification requestSpecification = RestAssured.given()
				.body(payload);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.patch(requestURI);
		return response;
	}

	public static Response deleteRequest(String requestURI) {
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.delete(requestURI);
		return response;
	}
}
