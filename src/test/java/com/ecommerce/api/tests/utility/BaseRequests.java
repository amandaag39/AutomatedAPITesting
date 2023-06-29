package com.ecommerce.api.tests.utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseRequests {

	public static Response getAllRequest(String requestURI, int limit) {
		RequestSpecification requestSpecification = RestAssured.given()
				.param("limit", limit);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.get(requestURI);
		return response;
	}

	public static Response getByIdRequest(String requestURI) {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.contentType(ContentType.JSON);
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

	public void patchRequest() {

	}

	public void deleteRequest() {

	}
}
