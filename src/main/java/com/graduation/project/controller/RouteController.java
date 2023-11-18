package com.graduation.project.controller;

import java.util.List;
import java.util.Set;

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

import com.azure.core.http.rest.Response;
import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.RoutePopularDTO;
import com.graduation.project.payload.request.RouteRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.RouteResponse;
import com.graduation.project.payload.response.RouteResponseForDropDown;
import com.graduation.project.service.RouteService;

@RestController
@RequestMapping
public class RouteController {

	@Autowired
	private RouteService routeService;
	
	@PostMapping("/route")
	private ResponseEntity<APIResponse> saveRoute(@RequestBody RouteRequest routeRequest){
		final APIResponse response = routeService.saveRoute(routeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/routes")
	private ResponseEntity<?> getAllRoute(@RequestParam Integer userId){
		final List<RouteResponse> response = routeService.getAllRoute(userId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response, true));
	}
	
	@GetMapping("/route/{id}")
	private ResponseEntity<APIResponse> getRoute(@PathVariable Integer id){
		final APIResponse response = routeService.getRoute(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/route/custom_name")
	private ResponseEntity<List<RouteResponseForDropDown>> getRouteCustomName(@RequestParam Integer userId){
		final List<RouteResponseForDropDown> response = routeService.getListRouteDropDown(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/route/{id}")
	private ResponseEntity<APIResponse> removeRoute(@PathVariable Integer id, @RequestParam Integer userId){
		final APIResponse response = routeService.removeRoute(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/route/search")
	private ResponseEntity<APIResponse> gePointRoute(){
		final Set<String>response =  routeService.getRouteToSearch();
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	
	@GetMapping("/route-popular")
	private ResponseEntity<APIResponse> getRoutePopular(){
		final Set<RoutePopularDTO> response = routeService.getAllRouteToShow();
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
}
