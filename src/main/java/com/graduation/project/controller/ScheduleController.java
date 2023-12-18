package com.graduation.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.request.DataCreateScheduleRequest;
import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;
import com.graduation.project.service.ScheduleService;

@RestController
@RequestMapping
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/schedule")
	private ResponseEntity<APIResponse> createSchedule(@RequestBody DataCreateScheduleRequest request){
		final APIResponse response = scheduleService.createSchedule(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/schedules")
	private ResponseEntity<APIResponse> getAllSchedule(@RequestParam Integer routeId){
		final APIResponse response = scheduleService.getSchedules(routeId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/schedulebytraveldate")
	private ResponseEntity<List<ScheduleResponseForDropDown>> getScheduleByTravelDate(@RequestParam LocalDate dateStart, @RequestParam Integer userId){
		final List<ScheduleResponseForDropDown> response = scheduleService.getScheduleByTravelDate(dateStart,userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/schedule")
	private ResponseEntity<APIResponse> updateSchedule(@RequestBody ScheduleRequest request) {
		final APIResponse response = scheduleService.updateSchedule(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
