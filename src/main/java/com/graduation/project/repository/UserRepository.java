package com.graduation.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	@Query(nativeQuery = true, value = "SELECT user.* FROM user, orders where user.id = orders.user_id and orders.id =:orderId")
	User findUserByOrderId(Integer orderId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM user where user.role_id = 2")
	List<User> findAllBrandOwner();
	
	@Query(nativeQuery = true, value = "SELECT * FROM user where user.role_id = 2 and user.active =:activeCode")
	List<User> findBrandOwnerFilter(Integer activeCode);
	
	@Query(nativeQuery = true, value = "SELECT * FROM user WHERE user.role_id = 3 AND user.anonymous = FALSE AND user.active = TRUE")
	List<User> findAllCustomer();
	
	@Query(nativeQuery = true, value = "SELECT * FROM user WHERE user.role_id = 3 AND user.anonymous = FALSE AND user.active = TRUE AND user.rank_id =:rankId")
	List<User> findCustomerByFilterRank(Integer rankId);
	
	@Query(nativeQuery = true,value = "select user.* from user,orders where user.id = orders.user_id and orders.id =:orderId")
	User findByOrderId(@Param("orderId") Integer orderId);
}
