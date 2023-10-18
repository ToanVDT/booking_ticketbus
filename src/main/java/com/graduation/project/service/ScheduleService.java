package com.graduation.project.service;

import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;

public interface ScheduleService {

	public APIResponse createSchedule(ScheduleRequest request);
	public APIResponse updateSchedule(ScheduleRequest request);
	public APIResponse getSchedules(Integer routeId);
}
