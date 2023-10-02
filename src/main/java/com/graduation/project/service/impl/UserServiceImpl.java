package com.graduation.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.AnonymousMapper;
import com.graduation.project.dto.AnonymousDTO;
import com.graduation.project.entity.Role;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.AnonymousRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.repository.RoleRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public APIResponse createUser(UserRequest userRequest) {
		APIResponse respone = new APIResponse();
		User user = new User();
		if (!userRepository.findUserByUsername(userRequest.getUsername()).isEmpty()) {
			respone.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
			respone.setSuccess(false);
			return respone;
		}
		if (userRepository.findUserByEmail(userRequest.getEmail()) != null) {
			respone.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
			respone.setSuccess(false);
			return respone;
		}
		if (userRepository.findUserByIdentityCode(userRequest.getIdentityCode()) != null) {
			respone.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
			respone.setSuccess(false);
			return respone;
		}
		if (userRepository.findUserByNumberPhone(userRequest.getPhone()) != null) {
			respone.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
			respone.setSuccess(false);
			return respone;
		}
		user.setUsername(userRequest.getUsername());
		user.setAddress(userRequest.getAddress());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setIdentityCode(userRequest.getIdentityCode());
		user.setActive(true);
		user.setAnonymous(false);
		user.setPoint(0);
		user.setPhoneNumber(userRequest.getPhone());
		user.setPassword(encoder.encode(userRequest.getPassword()));
		Role role = roleRepository.findRoleByName("ROLE_BRANDOWNER");
		user.setRole(role);
		userRepository.save(user);
		respone.setData(user);
		respone.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		respone.setSuccess(true);
		return respone;
	}

	@Override
	public APIResponse updateUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse createCustomer(CustomerRequest customerRequest) {
		APIResponse response = new APIResponse();
		User user = null;
		if(userRepository.findUserByNumberPhone(customerRequest.getPhoneNumber()) !=  null) {
			user = userRepository.findUserByNumberPhone(customerRequest.getPhoneNumber());
			user.setActive(true);
			user.setAnonymous(false);
			user.setIdentityCode(customerRequest.getIdentityCode());
			user.setUsername(customerRequest.getUsername());
			user.setPassword(customerRequest.getPassword());
			user.setPoint(0);
		}
		else {
			user = new User();
			if (!userRepository.findUserByUsername(customerRequest.getUsername()).isEmpty()) {
				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
				response.setSuccess(false);
				return response;
			}
			if (userRepository.findUserByEmail(customerRequest.getEmail()) != null) {
				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
				response.setSuccess(false);
				return response;
			}
			if (userRepository.findUserByIdentityCode(customerRequest.getIdentityCode()) != null) {
				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
				response.setSuccess(false);
				return response;
			}
			if (userRepository.findUserByNumberPhone(customerRequest.getPhoneNumber()) != null) {
				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
				response.setSuccess(false);
				return response;
			}
			user.setUsername(customerRequest.getUsername());
			user.setFirstName(customerRequest.getFirstName());
			user.setLastName(customerRequest.getLastName());
			user.setEmail(customerRequest.getEmail());
			user.setIdentityCode(customerRequest.getIdentityCode());
			user.setActive(true);
			user.setAnonymous(false);
			user.setPoint(0);
			user.setPhoneNumber(customerRequest.getPhoneNumber());
			user.setPassword(encoder.encode(customerRequest.getPassword()));
			Role role = roleRepository.findRoleByName("ROLE_CUSTOMER");
			user.setRole(role);
			userRepository.save(user);

		}
		response.setData(user);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse createAnonymous(AnonymousRequest request) {
		APIResponse apiResponse = new APIResponse();
		User anonymous = new User();
		anonymous.setFirstName(request.getFirstName());
		anonymous.setLastName(request.getLastName());
		anonymous.setEmail(request.getEmail());
		anonymous.setPhoneNumber(request.getPhoneNumber());
		anonymous.setActive(false);
		anonymous.setAddress(null);
		anonymous.setAnonymous(true);
		anonymous.setIdentityCode(null);
		anonymous.setPassword(null);
		anonymous.setUsername(null);
		anonymous.setPoint(0);
		userRepository.save(anonymous);
		AnonymousMapper mapper = new AnonymousMapper();
		AnonymousDTO dto = mapper.toDTO(anonymous);
		apiResponse.setData(dto);
		apiResponse.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		apiResponse.setSuccess(true);
		return apiResponse;
	}
	
	
}
