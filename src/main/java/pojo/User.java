package pojo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private String firstName;
	private String lastName;
	String maidenName;
	int age;
	String gender;
	private String email;
	private String phone;
}
