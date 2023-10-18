package com.graduation.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.response.APIResponse;
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
}
