package com.graduation.project.payload.response;

import java.time.LocalDate;
import java.time.LocalTime;
public interface ScheduleResponse {

	Integer getId();

	Integer getBusId();

	LocalDate getDateStart();

	LocalTime getStartTime();

	String getBusName();

	Double getPrice();

	Integer getSeats();

	Double getEatingFee();
	
	Integer getEmptySeats();
}
