package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {

	private String description;
	private Integer seats;
	private String identityCode;
	private String busType;
	private String name;
}
