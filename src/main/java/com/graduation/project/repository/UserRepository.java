package com.graduation.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("FROM User WHERE username=:username")
	Optional<User> findUserByUsername(String username);
	
	@Query("FROM User WHERE email=:email")
	User findUserByEmail(String email);
	
	@Query("FROM User WHERE identityCode=:identityCode")
	User findUserByIdentityCode(String identityCode);
	
	@Query("FROM User WHERE phoneNumber=:phone")
	User findUserByNumberPhone(String phone);
}
