package com.ecommerce.api.tests.utility.payload;

import com.ecommerce.api.tests.authentification.AuthData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class AuthDataBuilder {
	private static AuthData authData;

	public static String validAuthData() {
		AuthData authData = AuthData.builder()
				.username("kminchelle")
				.password("0lelplR")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String authDataJSON = objectMapper.writeValueAsString(authData);
			return authDataJSON;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String invalidAuthData() {
		Faker faker = new Faker();
		AuthData authData = AuthData.builder()
				.username(faker.name().username())
				.password(faker.internet().password())
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String authDataJSON = objectMapper.writeValueAsString(authData);
			return authDataJSON;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String emptyPayloadAuthData() {
		Faker faker = new Faker();
		AuthData authData = AuthData.builder()
				.username("")
				.password("")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String authDataJSON = objectMapper.writeValueAsString(authData);
			return authDataJSON;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
