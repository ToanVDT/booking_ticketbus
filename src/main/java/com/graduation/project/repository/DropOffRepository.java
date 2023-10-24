package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.DropOff;
import com.graduation.project.payload.response.DropOffResponse;

public interface DropOffRepository extends JpaRepository<DropOff, Integer>{

	@Query(nativeQuery = true, value = "SELECT id as id, dropoff_time as dropOffTime, dropoff_point as dropOffPoint FROM drop_off d WHERE d.shuttle_id=:shuttleId")
	List<DropOffResponse> findAllDropOff(Integer shuttleId);
}
