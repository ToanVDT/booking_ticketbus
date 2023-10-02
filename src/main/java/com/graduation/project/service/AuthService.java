package com.graduation.project.service;

import com.graduation.project.payload.request.LoginRequest;
import com.graduation.project.payload.request.TokenRefreshRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.TokenRefreshResponse;

public interface AuthService {

	public APIResponse signIn(LoginRequest loginRequest);

	public TokenRefreshResponse getRefreshtoken(TokenRefreshRequest tokenRefreshRequest);
	
	public Boolean CompareUsernameAndPassword(String username, String password);
	
	public Boolean getUserByUsername(String username);
}
