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

import com.graduation.project.payload.request.ChangePasswordRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UpdateProfileCustomerRequest;
import com.graduation.project.payload.request.UpdateProfileRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ProfileResponse;
import com.graduation.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	private ResponseEntity<APIResponse> createUser(@RequestBody UserRequest userRequest){
		final APIResponse response = userService.createUser(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@PostMapping("/customer")
	private ResponseEntity<APIResponse> createCustomer(@RequestBody CustomerRequest request){
		final APIResponse response = userService.createCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/profile")
	private ResponseEntity<ProfileResponse> getProfile(@RequestParam Integer userId) {
		final ProfileResponse response = userService.getProfileByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/password")
	private ResponseEntity<Boolean>  checkOldPasswordValid(@RequestParam Integer userId,@RequestParam String oldPassword ) {
		final Boolean response = userService.checkOldPassWordValid(userId,oldPassword);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/existUsername")
	private ResponseEntity<Boolean>  checkExistUsername(@RequestParam String username ) {
		final Boolean response = userService.checkExistUsername(username);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/existPhone")
	private ResponseEntity<Boolean>  checkExistPhone(@RequestParam String phone ) {
		final Boolean response = userService.checkExistPhone(phone);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/existEmail")
	private ResponseEntity<Boolean>  checkExistEmail(@RequestParam String email ) {
		final Boolean response = userService.checkExistEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/existIdentityCode")
	private ResponseEntity<Boolean>  checkExistIdentityCode(@RequestParam String identityCode ) {
		final Boolean response = userService.checkExistIdentityCode(identityCode);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/profile-customer")
	private ResponseEntity<APIResponse>  updateProfileCustomer(@RequestBody UpdateProfileCustomerRequest request ) {
		final APIResponse response = userService.updateProfileCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/profile-customer")
	private ResponseEntity<APIResponse>  getProfileCustomer(@RequestParam Integer userId ) {
		final APIResponse response = userService.getProfileCustomer(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/existPhoneRegisterCustomer")
	private ResponseEntity<Boolean>  checkExistPhoneRegisterCustomer(@RequestParam String phone ) {
		final Boolean response = userService.checkUsernameAndPhone(phone);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/changepassword")
	private ResponseEntity<APIResponse> changePassword(@RequestBody ChangePasswordRequest request) {
		final APIResponse response = userService.changePassword(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/update")
	private ResponseEntity<APIResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
		final APIResponse response = userService.updateProfile(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/rank")
	private ResponseEntity<String> getRankAccount(@RequestParam Integer userId){
		final String response = userService.getRankAccount(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}