package com.graduation.project.payload.response;

import java.time.LocalDate;
import java.time.LocalTime;
public interface ScheduleResponse {

	public Integer getId();
	public Integer getBusId();
	public LocalDate getDateStart();
	public LocalTime getStartTime();
	public String getBusName();
	public Double getPrice();
	public Integer getSeats();
	public Double getEatingFee();
}
