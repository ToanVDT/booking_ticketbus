package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

	private Integer userId;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phone;
	private String identityCode;
}
