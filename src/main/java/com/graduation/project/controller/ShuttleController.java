package com.graduation.project.controller;

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

import com.graduation.project.payload.request.ParkingRequest;
import com.graduation.project.payload.request.SearchShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequestUpdate;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.ShuttleService;

@RestController
@RequestMapping
public class ShuttleController {

	@Autowired
	private ShuttleService shuttleService;
	
	@PostMapping("/shuttle")
	private ResponseEntity<APIResponse> createShuttle(@RequestBody ShuttleRequest request){
		final APIResponse response = shuttleService.createShuttle(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/shuttles/search")
	private ResponseEntity<APIResponse> getShuttleAvailable(@RequestBody SearchShuttleRequest request){
		final APIResponse response = shuttleService.searchShuttle(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/shuttle")
	private ResponseEntity<APIResponse> updateShuttle(@RequestBody ShuttleRequestUpdate request){
		final APIResponse response = shuttleService.updateShuttle(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/shuttles")
	private ResponseEntity<APIResponse> getAllShuttle(@RequestParam Integer userId){
		final APIResponse response = shuttleService.getAllShuttle(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/shuttle")
	private ResponseEntity<APIResponse> getShuttleByRoute(@RequestParam Integer routeId){
		final APIResponse response = shuttleService.getShuttleByRoute(routeId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping("/shuttle/parking")
	private ResponseEntity<APIResponse> createParking(@RequestBody ParkingRequest request){
		final APIResponse response = shuttleService.createParkings(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
