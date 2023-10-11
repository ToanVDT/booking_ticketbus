package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

	private String name;
	private String image;
	private String phone;
	private String address;
	private String description;
}
