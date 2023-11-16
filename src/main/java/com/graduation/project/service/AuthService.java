package com.graduation.project.service;

import com.graduation.project.payload.request.LoginRequest;
import com.graduation.project.payload.request.TokenRefreshRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.TokenRefreshResponse;

public interface AuthService {

	 APIResponse signIn(LoginRequest loginRequest);

	 TokenRefreshResponse getRefreshtoken(TokenRefreshRequest tokenRefreshRequest);
	
	 Boolean CompareUsernameAndPassword(String username, String password);
	
	 Boolean getUserByUsername(String username);
}
