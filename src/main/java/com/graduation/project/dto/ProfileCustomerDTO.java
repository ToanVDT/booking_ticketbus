package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCustomerDTO {

	private String fullName;
	private Integer userId;
	private String email;
	private String phone;
}
