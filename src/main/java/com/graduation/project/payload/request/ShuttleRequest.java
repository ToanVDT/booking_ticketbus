package com.graduation.project.payload.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleRequest {

	private Integer id;
	private Integer busId;
	private Integer routeId;
	private Date startTime;
	private Date endTime;
	private Double price;
}
