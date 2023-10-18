package com.graduation.project.payload.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchShuttleRequest {

	private String startPoint;
	private String endPoint;
	private Date startTime;
}
