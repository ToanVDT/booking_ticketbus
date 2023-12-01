package com.graduation.project.service;

import java.util.List;

import com.graduation.project.dto.CustomerDTO;
import com.graduation.project.dto.ListBrandDTO;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.ChangePasswordRequest;
import com.graduation.project.payload.request.CustomerBookingRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UpdateProfileCustomerRequest;
import com.graduation.project.payload.request.UpdateProfileRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ProfileResponse;

public interface UserService {

	APIResponse createUser(UserRequest userRequest);

	APIResponse createCustomer(CustomerRequest customerRequest);

	void removeCustomer(Integer customerId);

	User createAnonymous(CustomerBookingRequest request);

	ProfileResponse getProfileByUserId(Integer userId);

	Boolean checkOldPassWordValid(Integer userId, String oldPassword);

	APIResponse changePassword(ChangePasswordRequest request);

	APIResponse updateProfile(UpdateProfileRequest request);

	Boolean checkExistEmail(String email);

	Boolean checkExistPhone(String phone);

	Boolean checkExistIdentityCode(String identityCode);

	Boolean checkExistUsername(String username);

	Boolean checkUsernameAndPhone(String phone);

	APIResponse getProfileCustomer(Integer userId);

	APIResponse updateProfileCustomer(UpdateProfileCustomerRequest request);
	
	String generateValidateCode(String email);
	
	void resetPassword(String email);
	
	List<ListBrandDTO> getAllCurrentBrand();
	
	List<ListBrandDTO> getCurrentBrandFilter(Integer activeCode);
	
	void activeAccount(Integer userId);
	
	void inactiveAccount(Integer userId);
	
	List<CustomerDTO> getAllCustomer();
	
	List<CustomerDTO> getCustomerByFilterRank(Integer rankId);
	
}
