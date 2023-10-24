package com.graduation.project.payload.request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleRequestUpdate {

	private Integer id;
	private LocalTime startTime;
	private LocalTime endTime;
}
