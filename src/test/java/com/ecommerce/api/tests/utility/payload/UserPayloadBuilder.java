package com.ecommerce.api.tests.utility.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import pojo.User;

public class UserPayloadBuilder {
	private static User user;
	public static String generatePayload() {
		Faker faker = new Faker();
		User user = User.builder()
				.firstName(faker.name().firstName())
				.lastName(faker.name().lastName())
				.maidenName(faker.name().lastName())
				.age(faker.number().numberBetween(18, 99))
				.gender(faker.demographic().sex())
				.email(faker.internet().emailAddress())
				.phone(faker.phoneNumber().cellPhone())
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String userJSON = objectMapper.writeValueAsString(user);
			return userJSON;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String generatePartialPayload() {
		Faker faker = new Faker();
		User user = User.builder()
				.firstName(faker.name().firstName())
				.lastName(faker.name().lastName())
				.email(faker.internet().emailAddress())
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String userJSON = objectMapper.writeValueAsString(user);
			return userJSON;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
