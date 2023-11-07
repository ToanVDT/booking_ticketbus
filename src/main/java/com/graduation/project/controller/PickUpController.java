package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.PickUpService;

@RestController
@RequestMapping
public class PickUpController {

	@Autowired
	private PickUpService pickUpService;
	
	@PostMapping("/pick_up")
	private ResponseEntity<APIResponse> updatePickUp(@RequestBody PickUpRequest request){
		final APIResponse response = pickUpService.updatePickUp(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/pick_up")
	private ResponseEntity<APIResponse>removePickUp(@RequestParam Integer id){
		final APIResponse response = pickUpService.removePickUp(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/pick_up")
	private ResponseEntity<APIResponse> getAllPickUp(@RequestParam Integer shuttleId){
		final APIResponse response = pickUpService.getAllPickUp(shuttleId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/pick_up")
	private ResponseEntity<APIResponse> addPickUp(@RequestParam Integer shuttleId, @RequestBody List<PickUpRequest> request){
		final APIResponse response = pickUpService.addPickUps(shuttleId, request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
