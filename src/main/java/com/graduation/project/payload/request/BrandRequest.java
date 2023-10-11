package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {

	private Integer id;
	private Integer userId;
	private String name;
	private String description;
	private String phone;
	private String image;
	private String address;
}
