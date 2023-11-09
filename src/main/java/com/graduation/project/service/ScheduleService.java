package com.graduation.project.service;

import java.time.LocalDate;
import java.util.List;

import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;

public interface ScheduleService {

	public APIResponse createSchedule(ScheduleRequest request);
	public APIResponse updateSchedule(ScheduleRequest request);
	public APIResponse getSchedules(Integer routeId);
	public List<ScheduleResponseForDropDown> getScheduleByTravelDate(LocalDate dateStart,Integer userId);
}
