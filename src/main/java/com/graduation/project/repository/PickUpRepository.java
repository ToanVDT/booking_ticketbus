package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.PickUp;
import com.graduation.project.payload.response.PickUpResponse;

public interface PickUpRepository extends JpaRepository<PickUp, Integer>{

	@Query(nativeQuery = true, value ="SELECT id as id, pickup_point as pickUpPoint, pickup_time as pickUpTime FROM pick_up p WHERE p.route_id=:routeId")
	List<PickUpResponse> findAllPickUp(Integer routeId);
}
