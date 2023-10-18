package com.graduation.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.RefreshToken;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.LoginRequest;
import com.graduation.project.payload.request.TokenRefreshRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.JwtRespone;
import com.graduation.project.payload.response.TokenRefreshResponse;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.security.jwt.JwtUtils;
import com.graduation.project.security.service.RefreshTokenService;
import com.graduation.project.security.service.UserDetailsImpl;
import com.graduation.project.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Value("${project.jwtExpirationMs}")
	private Integer jwtExpirationMs;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public APIResponse signIn(LoginRequest loginRequest) {
		APIResponse response = new APIResponse();
		JwtRespone jwtResponse = new JwtRespone();
		if(!CompareUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())){
			response.setMessage(ConstraintMSG.LOGIN_FAIL_MSG);
			response.setData(null);
			response.setSuccess(false);
			return response;
		}
		else if (!getUserByUsername(loginRequest.getUsername())) {
			response.setMessage(ConstraintMSG.ACCOUNT_INACTIVE_MSG);
			response.setData(null);
			response.setSuccess(false);
			return response;
		} 
		else  {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			String jwt = jwtUtils.generateJwtToken(userDetails.getUsername(), jwtExpirationMs);

			RefreshToken refreshToken = refreshTokenService.getByUserId(userDetails.getId());
			if (refreshToken != null) {
				refreshTokenService.deleteByUserId(userDetails.getId());
			}
			refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			jwtResponse.setId(userDetails.getId());
			jwtResponse.setEmail(userDetails.getEmail());
			jwtResponse.setUsername(userDetails.getUsername());
			jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
			jwtResponse.setAccessToken(jwt);
			jwtResponse.setRoles(roles);
			response.setData(jwtResponse);
			response.setMessage(ConstraintMSG.LOGIN_SUCCESS_MSG);
			response.setSuccess(true);
			return response;
		}
	}

	@Override
	public TokenRefreshResponse getRefreshtoken(TokenRefreshRequest tokenRefreshRequest) {
		TokenRefreshResponse refreshResponse = new TokenRefreshResponse();
		String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
		RefreshToken refreshToken = refreshTokenService.getByToken(requestRefreshToken).orElse(null);
		if (refreshToken != null) {
			refreshToken = refreshTokenService.verifyExpiration(refreshToken);
			User user = refreshToken.getUser();
			String token = jwtUtils.generateJwtToken(user.getUsername(), jwtExpirationMs);
			refreshResponse.setAccessToken(token);
			refreshResponse.setRefreshToken(refreshToken.getRefreshToken());
		}
		return refreshResponse;
	}

	@Override
	public Boolean CompareUsernameAndPassword(String username, String password) {
		User user = userRepository.findUserByUsername(username).orElse(null);
		if(user == null) {
			return false;
		}
		else if (!encoder.matches(password, user.getPassword())){
			return false;
		}
		return true;
	}

	@Override
	public Boolean getUserByUsername(String username) {
		User user = userRepository.findUserByUsername(username).orElse(null);
		if(!user.getActive())
			return false;
		return true;
	}

}
