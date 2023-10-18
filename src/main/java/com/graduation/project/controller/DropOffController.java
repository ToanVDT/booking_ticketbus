package com.graduation.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private ResponseEntity<APIResponse> getAllDropOff(@RequestParam Integer routeId){
		final APIResponse response = dropOffService.getAllDropOff(routeId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/drop_off/{id}")
	private ResponseEntity<APIResponse> removeDropOff(@PathVariable Integer id, @RequestParam Integer routeId){
		final APIResponse response = dropOffService.removeDropOff(id, routeId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
