package com.graduation.project.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleDTO {

	private String routeName;
	private LocalTime startTime;
	private LocalTime travelTime;
}
