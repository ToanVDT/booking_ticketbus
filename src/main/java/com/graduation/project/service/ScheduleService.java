package com.graduation.project.service;

import java.time.LocalDate;
import java.util.List;

import com.graduation.project.payload.request.DataCreateScheduleRequest;
import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;

public interface ScheduleService {

	APIResponse createSchedule(DataCreateScheduleRequest request);

	APIResponse updateSchedule(ScheduleRequest request);

	APIResponse getSchedules(Integer routeId);

	 List<ScheduleResponseForDropDown> getScheduleByTravelDate(LocalDate dateStart,Integer userId);
}
