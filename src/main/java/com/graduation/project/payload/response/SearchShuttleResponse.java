package com.graduation.project.payload.response;

import java.util.Date;

public interface SearchShuttleResponse {

	public Integer getEmptySeats();
	public Integer getShuttleId();
	public Double getPrice();
	public Double getEatingFee();
	public Integer getSeats();
	public String getImage();
	public String getBrandName();
	public String getType();
	public Integer getBusId();
	public Date getStartTime();
	public Date getEndTime();
	public Integer getRouteId();
	public String getStartPoint();
	public String getEndPoint();
	public Integer getScheduleId();
}
