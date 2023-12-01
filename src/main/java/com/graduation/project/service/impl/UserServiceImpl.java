package com.graduation.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.common.Utility;
import com.graduation.project.config.AnonymousMapper;
import com.graduation.project.dto.AnonymousDTO;
import com.graduation.project.dto.CustomerDTO;
import com.graduation.project.dto.ListBrandDTO;
import com.graduation.project.dto.ProfileCustomerDTO;
import com.graduation.project.entity.Brand;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.Role;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.ChangePasswordRequest;
import com.graduation.project.payload.request.CustomerBookingRequest;
import com.graduation.project.payload.request.CustomerRequest;
import com.graduation.project.payload.request.UpdateProfileCustomerRequest;
import com.graduation.project.payload.request.UpdateProfileRequest;
import com.graduation.project.payload.request.UserRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ProfileResponse;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.RankingRepository;
import com.graduation.project.repository.RoleRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.service.EmailService;
import com.graduation.project.service.GiftCodeService;
import com.graduation.project.service.UserService;
import com.graduation.project.thread.MailWelcomeNewJoinerThread;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RankingRepository rankingRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private GiftCodeService giftCodeService;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private MailWelcomeNewJoinerThread mailWelcomeNewJoinerThread;

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
		try {
			if (userRepository.findUserByNumberPhone(customerRequest.getPhoneNumber()) != null) {
				user = userRepository.findUserByNumberPhone(customerRequest.getPhoneNumber());
			} else {
				user = new User();
			}
			user.setUsername(customerRequest.getPhoneNumber());
			user.setFirstName(customerRequest.getFirstName());
			user.setLastName(customerRequest.getLastName());
			user.setEmail(customerRequest.getEmail());
			user.setActive(true);
			user.setAnonymous(false);
			user.setPoint(0);
			user.setPhoneNumber(customerRequest.getPhoneNumber());
			user.setPassword(encoder.encode(customerRequest.getPassword()));
			Role role = roleRepository.findRoleByName("ROLE_CUSTOMER");
			user.setRole(role);
			Ranking ranking = rankingRepository.findById(ConstraintMSG.RANK_NEW_MEMBER).orElse(null);
			user.setRank(ranking);
			userRepository.save(user);
			GiftCode codeGenerated = (GiftCode) giftCodeService.saveGiftCode1Time(ranking.getId(), user.getId()).getData();
			mailWelcomeNewJoinerThread.setCodeGenerated(codeGenerated);
			mailWelcomeNewJoinerThread.setRanking(ranking);
			mailWelcomeNewJoinerThread.setUser(user);
			taskExecutor.execute(mailWelcomeNewJoinerThread);
			response.setData(user);
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public void removeCustomer(Integer customerId) {
		User user = userRepository.findById(customerId).orElse(null);
		user.setActive(false);
		userRepository.save(user);
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
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
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
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistPhone(String phone) {
		User user = userRepository.findUserByNumberPhone(phone);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistIdentityCode(String identityCode) {
		User user = userRepository.findUserByIdentityCode(identityCode);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkExistUsername(String username) {
		User user = userRepository.findUserByUsername(username).orElse(null);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkUsernameAndPhone(String phone) {
		User user = userRepository.findUserByNumberPhone(phone);
		if (user == null) {
			return false;
		} else if (user != null && user.getAnonymous()) {
			return false;
		}
		return true;
	}

	@Override
	public APIResponse getProfileCustomer(Integer userId) {
		APIResponse response = new APIResponse();
		try {
			User user = userRepository.findById(userId).orElse(null);
			ProfileCustomerDTO dto = new ProfileCustomerDTO();
			dto.setUserId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setPhone(user.getPhoneNumber());
			dto.setFullName(user.getLastName() + ' ' + user.getFirstName());
			response.setData(dto);
			response.setMessage(ConstraintMSG.GET_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse updateProfileCustomer(UpdateProfileCustomerRequest request) {
		APIResponse response = new APIResponse();
		try {
			User user = userRepository.findById(request.getUserId()).orElse(null);
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String generateValidateCode(String email) {
		User user = userRepository.findUserByEmail(email);
		String validateCode = null;
		if (user != null) {
			validateCode = Utility.RandomValidateCode();
			emailService.sendMailForgotPassword(user, validateCode);
		}
		return validateCode;
	}

	@Override
	public void resetPassword(String email) {
		User user = userRepository.findUserByEmail(email);
		user.setPassword(encoder.encode("123456"));
		userRepository.save(user);
	}

	@Override
	public List<ListBrandDTO> getAllCurrentBrand() {
		List<User> users = userRepository.findAllBrandOwner();
		List<ListBrandDTO> result = new ArrayList<>();
		result = getCurrentBrand(users);
		return result;
	}
	@Override
	public List<ListBrandDTO> getCurrentBrandFilter(Integer activeCode){
		List<User> users = userRepository.findBrandOwnerFilter(activeCode);
		List<ListBrandDTO> result = new ArrayList<>();
		result = getCurrentBrand(users);
		return result;
	}
	public List<ListBrandDTO> getCurrentBrand(List<User> users){
		List<ListBrandDTO> result = new ArrayList<>();
		Brand brand = null;
		ListBrandDTO dto = null;
		for(User user:users) {
			brand = brandRepository.findByUserId(user.getId());
			dto = new ListBrandDTO();
			dto.setUserId(user.getId());
			dto.setAccountStatus(user.getActive()==true?ConstraintMSG.ACCOUNT_ACTIVE:ConstraintMSG.ACCOUNT_INACTIVE);
			dto.setEmail(user.getEmail());
			dto.setFullName(user.getLastName()+" "+ user.getFirstName());
			dto.setIdentityCode(user.getIdentityCode());
			dto.setPhone(user.getPhoneNumber());
			dto.setUsername(user.getUsername());
			if(brand == null) {
				dto.setAddress(ConstraintMSG.BRAND_OWNER_UPDATEDATA_YET);
				dto.setNameBrand(ConstraintMSG.BRAND_OWNER_UPDATEDATA_YET);
				dto.setPhoneBrand(ConstraintMSG.BRAND_OWNER_UPDATEDATA_YET);
			}
			else {
				dto.setAddress(brand.getAddress());
				dto.setNameBrand(brand.getBrandName());
				dto.setPhoneBrand(brand.getPhoneBrand());
			}
			result.add(dto);
		}
		return result;
	}

	@Override
	public void activeAccount(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		user.setActive(true);
		userRepository.save(user);
	}

	@Override
	public void inactiveAccount(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	public List<CustomerDTO> getAllCustomer() {
		List<User> users = userRepository.findAllCustomer();
		List<CustomerDTO> dtos = new ArrayList<>();
		dtos = getCustomer(users);
		return dtos;
	}
	@Override
	public List<CustomerDTO> getCustomerByFilterRank(Integer rankId){
		List<User> users = userRepository.findCustomerByFilterRank(rankId);
		List<CustomerDTO> dtos = new ArrayList<>();
		dtos = getCustomer(users);
		return dtos;
	}
	public List<CustomerDTO> getCustomer(List<User> users){
		List<CustomerDTO> dtos = new ArrayList<>();
		CustomerDTO dto = null;
		for(User user:users) {
			dto = new CustomerDTO();
			dto.setCustomerId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setFullName(user.getLastName()+" "+user.getFirstName());
			dto.setPhone(user.getPhoneNumber());
			dto.setRank(user.getRank().getRankName().equals(ConstraintMSG.RANKNAME_NEWMEMBER) ? ConstraintMSG.RANKNAME_NEWMEMBER_CONVERT :user.getRank().getRankName().equals(ConstraintMSG.RANKNAME_MEMBER)  ? ConstraintMSG.RANKNAME_MEMBER_CONVERT:ConstraintMSG.RANKNAME_VIPPER_CONVERT);
			dtos.add(dto);
		}
		return dtos;
	}
}
