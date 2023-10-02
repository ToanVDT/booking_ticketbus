package com.graduation.project.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.graduation.project.entity.RefreshToken;
import com.graduation.project.entity.User;
import com.graduation.project.exception.TokenRefreshException;
import com.graduation.project.repository.RefreshTokenRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.security.jwt.JwtUtils;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {

	@Value("${project.jwtRefreshExpirationMs}")
	private Integer refreshTokenDurationMs;

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	public Optional<RefreshToken> getByToken(String token) {
		return refreshTokenRepository.findByRefreshToken(token);
	}
	
	public RefreshToken getByUserId(Integer userId) {
		return refreshTokenRepository.findByUserId(userId);
	}

	public RefreshToken createRefreshToken(Integer userId) {
		
		RefreshToken refreshToken = new RefreshToken();
		User user = userRepository.findById(userId).get();
		refreshToken.setUser(user);
		refreshToken.setRefreshToken(jwtUtils.generateJwtToken(user.getUsername(),refreshTokenDurationMs));
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (!jwtUtils.validateJwtToken(token.getRefreshToken())) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getRefreshToken(), "Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	@Transactional
	public int deleteByUserId(Integer userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}
}
