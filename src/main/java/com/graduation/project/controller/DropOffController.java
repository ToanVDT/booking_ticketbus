package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.DropOffService;

@RestController
@RequestMapping
public class DropOffController {

	@Autowired
	private DropOffService dropOffService;
	
	@PostMapping("/drop_off")
	private ResponseEntity<APIResponse> saveDropOff(@RequestBody DropOffRequest dropOffRequest){
		final APIResponse response = dropOffService.updateDropOff(dropOffRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/drop_offs")
	private ResponseEntity<APIResponse> getAllDropOff(@RequestParam Integer shuttleId){
		final APIResponse response = dropOffService.getAllDropOff(shuttleId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/drop_off/{id}")
	private ResponseEntity<APIResponse> removeDropOff(@PathVariable Integer id, @RequestParam Integer shuttleId){
		final APIResponse response = dropOffService.removeDropOff(id, shuttleId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/drop_off")
	private ResponseEntity<APIResponse> addDropOff(@RequestParam Integer shuttleId, @RequestBody List<DropOffRequest> requests){
		final APIResponse response = dropOffService.addDropOffs(shuttleId, requests);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
