package com.graduation.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.payload.request.LoginRequest;
import com.graduation.project.payload.request.TokenRefreshRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.TokenRefreshResponse;
import com.graduation.project.security.service.RefreshTokenService;
import com.graduation.project.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@PostMapping("/signin")
	private ResponseEntity<APIResponse> Login(@RequestBody LoginRequest loginRequest) {
		final APIResponse response = authService.signIn(loginRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<TokenRefreshResponse> refreshtoken(@RequestBody TokenRefreshRequest tokenRefreshRequest){
		final TokenRefreshResponse respone = authService.getRefreshtoken(tokenRefreshRequest);
		return ResponseEntity.status(HttpStatus.OK).body(respone);
	}
}
