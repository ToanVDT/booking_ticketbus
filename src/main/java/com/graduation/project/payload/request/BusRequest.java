package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRequest {

	private Integer id;
	private Integer brandId;
	private Integer typeId;
	private Integer seats;
	private String description;
	private String identityCode;
	private String name;
}
