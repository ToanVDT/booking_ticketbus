package com.graduation.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.AnonymousMapper;
import com.graduation.project.dto.AnonymousDTO;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.Role;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.ChangePasswordRequest;
import com.graduation.project.payload.request.CustomerBookingRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UpdateProfileRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ProfileResponse;
import com.graduation.project.repository.RankingRepository;
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
	
	@Autowired
	private RankingRepository rankingRepository;
	
	@Override
	public APIResponse createUser(UserRequest userRequest) {
		APIResponse respone = new APIResponse();
		User user = new User();
		Ranking ranking = rankingRepository.findById(1).orElse(null);
		user.setRank(ranking);
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
	public User createAnonymous(CustomerBookingRequest request) {
		APIResponse apiResponse = new APIResponse();
		User anonymous = new User();
		Ranking ranking = rankingRepository.findById(ConstraintMSG.RANK_NEW_MEMBER).get();
		anonymous.setRank(ranking);
		Role role = roleRepository.findRoleByName("ROLE_CUSTOMER");
		anonymous.setRole(role);
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
		return anonymous;
	}

	@Override
	public ProfileResponse getProfileByUserId(Integer userId) {
		ProfileResponse profileResponse = userRepository.findProfileByUserId(userId);
		return profileResponse;
	}

	@Override
	public Boolean checkOldPassWordValid(Integer userId, String oldPassword) {
			User user =  userRepository.findById(userId).orElse(null);
			if(user !=null) {
				if (encoder.matches(oldPassword, user.getPassword())) {
					return true;
				}
			}
		return false;
	}

	@Override
	public APIResponse changePassword(ChangePasswordRequest request) {
		APIResponse response = new APIResponse();
		User user = userRepository.findById(request.getUserId()).orElse(null);
		user.setPassword(encoder.encode(request.getNewPassword()));
		userRepository.save(user);
		response.setSuccess(true);
		response.setMessage(ConstraintMSG.CHANGE_PASSWORD_SUCCESS);
		return response;
	}

	@Override
	public APIResponse updateProfile(UpdateProfileRequest request) {
		APIResponse response = new APIResponse();
		try {
			User user = userRepository.findById(request.getUserId()).orElse(null);
			user.setAddress(request.getAddress());
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setIdentityCode(request.getIdentityCode());
			user.setLastName(request.getLastName());
			user.setPhoneNumber(request.getPhone());
			userRepository.save(user);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Boolean checkExistEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		if(user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistPhone(String phone) {
		User user = userRepository.findUserByNumberPhone(phone);
		if(user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistIdentityCode(String identityCode) {
		User user = userRepository.findUserByIdentityCode(identityCode);
		if(user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistUsername(String username) {
		User user = userRepository.findUserByUsername(username).orElse(null);
		if(user == null) {
			return false;
		}
		return true;
	}
}
