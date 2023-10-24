package com.graduation.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.impl.TypeService;

@RestController
@RequestMapping
public class TypeController {

	@Autowired
	private TypeService typeService;
	
	@GetMapping("/types")
	public ResponseEntity<APIResponse> getTypes(){
		final APIResponse response = typeService.getAllType();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
