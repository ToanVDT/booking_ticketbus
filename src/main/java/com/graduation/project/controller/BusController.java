package com.graduation.project.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.BusResponse;
import com.graduation.project.payload.response.BusResponseForDropDown;
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
	private ResponseEntity<APIResponse> getAllBus(@RequestParam Integer userId){
		final List<BusResponse> response = busService.getAllBusInBrand(userId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	
	@GetMapping("/bus/{id}")
	private ResponseEntity<APIResponse> getBus(@PathVariable Integer id){
		final APIResponse response = busService.getBus(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/bus/dropdown")
	private ResponseEntity<List<BusResponseForDropDown>> getBusForDropDown(@RequestParam Integer userId){
		final List<BusResponseForDropDown> response = busService.getBusForDropDown(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/bus/dropdown1")
	private ResponseEntity<List<BusResponseForDropDown>> getBusForDropDownByTravelDate(@RequestParam Integer userId, @RequestParam LocalDate travelDate){
		final List<BusResponseForDropDown> response = busService.getBusAvailableByTravelDate(userId, travelDate);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/bus/{id}")
	private ResponseEntity<APIResponse> removeBus(@PathVariable Integer id){
		final APIResponse response = busService.removeBus(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/bus/duplicateName")
	private Boolean checkDuplicateName(@RequestParam String name) {
		final Boolean response = busService.checkDuplicateBusName(name);
		return response;
	}
	@GetMapping("/bus/duplicateIdentityCode")
	private Boolean checkDuplicateIdentityCode(@RequestParam String identityCode) {
		final Boolean response = busService.checkDuplicateIdentityCode(identityCode);
		return response;
	}
}
