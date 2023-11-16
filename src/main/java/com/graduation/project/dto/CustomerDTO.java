package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Integer customerId;
	private String fullName;
	private String email;
	private String phone;
	private String rank;
}
