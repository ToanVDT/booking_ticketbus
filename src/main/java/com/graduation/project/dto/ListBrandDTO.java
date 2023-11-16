package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListBrandDTO {

	private Integer userId;
	private String fullName;
	private String email;
	private String username;
	private String identityCode;
	private String phone;
	private String address;
	private String nameBrand;
	private String accountStatus;
	private String phoneBrand;
}
