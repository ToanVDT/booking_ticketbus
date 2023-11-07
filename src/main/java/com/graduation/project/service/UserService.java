package com.graduation.project.service;

import com.graduation.project.entity.User;
import com.graduation.project.payload.request.ChangePasswordRequest;
import com.graduation.project.payload.request.CustomerBookingRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UpdateProfileRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ProfileResponse;

public interface UserService {

	public APIResponse createUser(UserRequest userRequest);
	public APIResponse createCustomer(CustomerRequest customerRequest);
	public APIResponse removeUser();
	public User createAnonymous(CustomerBookingRequest request);
	public ProfileResponse getProfileByUserId(Integer userId);
	public Boolean checkOldPassWordValid(Integer userId, String oldPassword);
	public APIResponse changePassword(ChangePasswordRequest request);
	public APIResponse updateProfile(UpdateProfileRequest request);
	public Boolean checkExistEmail(String email);
	public Boolean checkExistPhone(String phone);
	public Boolean checkExistIdentityCode(String identityCode);
	public Boolean checkExistUsername(String username);
	public Boolean checkUsernameAndPhone(String phone);
}
