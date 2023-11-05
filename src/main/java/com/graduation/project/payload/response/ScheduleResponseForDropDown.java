package com.graduation.project.payload.response;

import java.time.LocalTime;

public interface ScheduleResponseForDropDown {

	public Integer getId();
	public String getRouteName();
	public LocalTime getStartTime();
	public Integer getShuttleId();
}
