package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.PickUp;
import com.graduation.project.payload.response.PickUpResponse;

import jakarta.transaction.Transactional;

public interface PickUpRepository extends JpaRepository<PickUp, Integer>{

	@Query(nativeQuery = true, value ="SELECT id as id, pickup_point as pickUpPoint, pickup_time as pickUpTime FROM pick_up p WHERE p.shuttle_id=:shuttleId")
	List<PickUpResponse> findAllPickUp(Integer shuttleId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from pick_up where pick_up.shuttle_id=:shuttleId")
	void deletePickUp(Integer shuttleId);
}
