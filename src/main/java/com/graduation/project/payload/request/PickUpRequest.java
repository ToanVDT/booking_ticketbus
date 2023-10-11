package com.graduation.project.payload.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickUpRequest {

	private Integer id;
	private Integer routeId;
	private String pickUpPoint;
	private Date pickUpTime;
}
