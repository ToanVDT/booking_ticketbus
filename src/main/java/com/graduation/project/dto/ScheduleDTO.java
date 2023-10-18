package com.graduation.project.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

	private String busName;
	private Double price;
	private Date startTime;
	private Date endTime;
	private Integer seats;
}
