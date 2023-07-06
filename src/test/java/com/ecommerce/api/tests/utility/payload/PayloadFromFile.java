package com.ecommerce.api.tests.utility.payload;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PayloadFromFile {
	public static String generatePayload(String resource) {
		String filePath =
				System.getProperty("user.dir") + "/src/test/resources/payloadjson/" + resource +
						".json";
		try {
			File file = new File(filePath);
			return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
