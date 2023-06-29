package com.ecommerce.api.tests.utility;

public class URICreator {
	public final static String baseURI = "https://dummyjson.com/";

	public static String getBaseURI() {
		return baseURI;
	}

	// Overloaded method to provide path to resources
	public static String getBaseURI(String resourcePath) {
		return baseURI + resourcePath;
	}
}
