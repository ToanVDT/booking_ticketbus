package com.graduation.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.project.payload.request.BrandRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.BrandService;
@RestController
@RequestMapping
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@PostMapping(value ="/brand", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	private ResponseEntity<APIResponse> saveBrand(@RequestParam(value = "file", required = false) MultipartFile[] files, @RequestParam("data") String brandRequest) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		BrandRequest request = mapper.readValue(brandRequest, BrandRequest.class);
		final APIResponse response = brandService.saveBrand(request, files);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/brands")
	private ResponseEntity<APIResponse> getAllBrand(){
		final APIResponse response = brandService.getAllBrand();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/brand/{id}")
	private ResponseEntity<APIResponse> getBrand(@PathVariable Integer id){
		final APIResponse response = brandService.getBrand(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/brand")
	private ResponseEntity<APIResponse> getBrandByUserId(@RequestParam Integer userId){
		final APIResponse response = brandService.getBrandByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/brand/phone")
	private ResponseEntity<Boolean> checkDuplicatePhone(@RequestParam String phone, @RequestParam Integer userId){
		final Boolean response = brandService.getBrandByPhone(phone,userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/brand/name")
	private ResponseEntity<Boolean> checkDuplicateName(@RequestParam String name,@RequestParam Integer userId){
		final Boolean response = brandService.getBrandByBrandName(name,userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
