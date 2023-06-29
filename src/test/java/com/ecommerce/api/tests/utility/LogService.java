package com.ecommerce.api.tests.utility;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogService {
	public static void logData(String payload, Response response) {
		Logger logger = LogManager.getLogger();
		logger.info("===================");
		logger.info("Payload: " + payload);
		logger.info("Response body: " + response.getBody().asString());
	}

	public static void logData(Response response) {
		Logger logger = LogManager.getLogger();
		logger.info("===================");
		logger.info("Response body: " + response.getBody().asString());
	}
}
