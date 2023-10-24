package com.graduation.project.payload.response;

import java.time.LocalTime;

public interface ShuttleResponse {

	public Integer getId();
	public LocalTime getStartTime();
	public LocalTime getEndTime();
	public String getRouteName();
}
