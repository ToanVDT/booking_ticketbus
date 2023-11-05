package com.graduation.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.User;
import com.graduation.project.payload.response.ProfileResponse;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("FROM User WHERE username=:username")
	Optional<User> findUserByUsername(String username);
	
	@Query("FROM User WHERE email=:email")
	User findUserByEmail(String email);
	
	@Query("FROM User WHERE identityCode=:identityCode")
	User findUserByIdentityCode(String identityCode);
	
	@Query("FROM User WHERE phoneNumber=:phone")
	User findUserByNumberPhone(String phone);
	
	@Query(nativeQuery = true, value = "SELECT concat(user.last_name,' ',user.first_name) as fullName, user.username,user.address, user.email,user.phone_number as phone, user.identity_code as identityCode FROM user where user.id=:userId")
	ProfileResponse findProfileByUserId(Integer userId);
}
