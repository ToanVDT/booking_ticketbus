package com.graduation.project.payload.response;

import java.util.Date;

public interface ShuttleResponse {

	public Date getStarTime();
	public Date getEndTime();
	public String getBusName();
	public Double getPrice();
	public Integer getSeats();
}
