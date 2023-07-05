package com.ecommerce.api.tests.authentification;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthData {
	private String username;
	private String password;
}
