package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Bus;
import com.graduation.project.payload.response.BusResponse;

public interface BusRepository extends JpaRepository<Bus, Integer>{

	@Query(nativeQuery = true, value = "SELECT b.id as id, b.seats as seats, b.identity_code as identityCode, b.description as description, t.type as busType FROM bus b, bus_type t WHERE t.id = b.type_id and b.brand_id=:brandId")
	List<BusResponse> findAllBus(Integer brandId);
}
