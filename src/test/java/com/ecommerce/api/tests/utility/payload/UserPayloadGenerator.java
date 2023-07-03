package com.ecommerce.api.tests.utility.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import pojo.User;


public class UserPayloadGenerator {
	public static String generatePayload() {
		Faker faker = new Faker();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String maidenName = faker.name().lastName();
		int age = faker.number().numberBetween(18, 99);
		String gender = faker.demographic().sex();
		String email = faker.internet().emailAddress();
		String phone = faker.phoneNumber().cellPhone();

		User user = new User(firstName, lastName, maidenName, age, gender, email, phone);
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
