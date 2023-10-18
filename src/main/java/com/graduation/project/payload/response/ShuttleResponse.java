package com.graduation.project.payload.response;

import java.time.LocalTime;

public interface ShuttleResponse {

	public LocalTime getStarTime();
	public LocalTime getTravelTime();
	public String getRouteName();
}
