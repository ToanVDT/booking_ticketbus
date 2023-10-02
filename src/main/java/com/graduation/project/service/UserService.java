package com.graduation.project.service;

import com.graduation.project.payload.request.AnonymousRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;

public interface UserService {

	public APIResponse createUser(UserRequest userRequest);
	public APIResponse updateUser(UserRequest userRequest);
	public APIResponse createCustomer(CustomerRequest customerRequest);
	public APIResponse removeUser();
	public APIResponse createAnonymous(AnonymousRequest request);
}
