package com.graduation.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.graduation.project.entity.RefreshToken;
import com.graduation.project.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

	Optional<RefreshToken> findByRefreshToken(String refreskToken);

	RefreshToken findByUserId(Integer userId);

	@Modifying
	int deleteByUser(User user);
}
