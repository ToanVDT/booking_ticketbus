package com.graduation.project.payload.request;

import lombok.Data;

@Data
public class FilterOrderRequest {

	private Integer scheduleId;
	
	private Integer isPaid;
	
	private String status;
}
