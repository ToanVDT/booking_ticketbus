package com.graduation.project.payload.request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickUpRequest {

	private Integer pickUpId;
	private String pickUpPoint;
	private LocalTime pickUpTime;
}
