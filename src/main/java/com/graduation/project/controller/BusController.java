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

import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.BusService;

@RestController
@RequestMapping
public class BusController {

	@Autowired
	private BusService busService;
	
	@PostMapping("/bus")
	private ResponseEntity<APIResponse> saveBus(@RequestBody BusRequest request){
		final APIResponse response = busService.saveBus(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/bus")
	private ResponseEntity<APIResponse> getAllBus(@RequestParam Integer brandId){
		final APIResponse response = busService.getAllBusInBrand(brandId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/bus/{id}")
	private ResponseEntity<APIResponse> getBus(@PathVariable Integer id){
		final APIResponse response = busService.getBus(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/bus/{id}")
	private ResponseEntity<APIResponse> removeBus(@PathVariable Integer id, @RequestParam Integer userId){
		final APIResponse response = busService.removeBus(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
