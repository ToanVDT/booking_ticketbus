package com.graduation.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoOrderByOrderCode {

	private Integer routeId;
	private Integer shuttleId;
	private Integer scheduleId;
	private String routeName;
	private LocalDate dateStart;
	private LocalTime startTime;
}
