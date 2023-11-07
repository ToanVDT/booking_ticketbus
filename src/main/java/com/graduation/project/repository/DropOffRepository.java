package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.DropOff;
import com.graduation.project.payload.response.DropOffResponse;

import jakarta.transaction.Transactional;

public interface DropOffRepository extends JpaRepository<DropOff, Integer>{

	@Query(nativeQuery = true, value = "SELECT id as id, dropoff_time as dropOffTime, dropoff_point as dropOffPoint FROM drop_off d WHERE d.shuttle_id=:shuttleId")
	List<DropOffResponse> findAllDropOff(Integer shuttleId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from drop_off where drop_off.shuttle_id=:shuttleId")
	void deleteDropOff(Integer shuttleId);
}
