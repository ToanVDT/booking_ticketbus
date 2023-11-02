package com.graduation.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.dto.SeatDTO;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SeatEmptyResponse;
import com.graduation.project.service.SeatService;

@RestController
@RequestMapping
public class SeatController {

	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seats")
	private ResponseEntity<APIResponse> getSeatInShuttle(@RequestParam Integer scheduleId){
		final APIResponse response = seatService.getSeatInShuttle(scheduleId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/seat")
	private ResponseEntity<APIResponse> getSeatWithScheduleId(@RequestParam Integer scheduleId){
		final List<SeatDTO> response = seatService.getSeatWithScheduleId(scheduleId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("getdata", response,true));
	}
	@GetMapping("/empty_seat")
	private ResponseEntity<SeatEmptyResponse> getSeatEmpty(@RequestParam LocalDate dateStart,@RequestParam Integer scheduleId){
		final SeatEmptyResponse response = seatService.getSeatEmpty(dateStart,scheduleId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
