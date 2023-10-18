package com.graduation.project.payload.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

	private Integer id;
	private Integer busId;
	private Integer shuttleId;
	private LocalDate travelDate;
	private Double price;
}
