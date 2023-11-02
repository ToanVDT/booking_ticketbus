package com.graduation.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Bus;
import com.graduation.project.payload.response.BusResponse;
import com.graduation.project.payload.response.BusResponseForDropDown;

public interface BusRepository extends JpaRepository<Bus, Integer>{

	@Query(nativeQuery = true, value = "SELECT b.id as id, b.seats as seats,b.name as name, b.identity_code as identityCode, b.description as description, t.type as busType FROM bus b, bus_type t WHERE t.id = b.type_id and b.brand_id=:brandId")
	List<BusResponse> findAllBus(Integer brandId);
	
	@Query(nativeQuery = true, value = "SELECT bus.id, bus.name FROM bus WHERE bus.brand_id =:brandId and bus.id  NOT in  ( SELECT bus.id FROM bus, shuttle, schedule where bus.id = schedule.bus_id and shuttle.id = schedule.shuttle_id and now() < DATE_ADD(schedule.date_start, INTERVAL 1 DAY) )")
	List<BusResponseForDropDown> findBusDropDown(Integer brandId);
	
	@Query(nativeQuery = true, value = "SELECT bus.id, bus.name FROM bus WHERE bus.brand_id =:brandId and bus.id NOT in ( SELECT bus.id FROM bus, shuttle, schedule where bus.id = schedule.bus_id and shuttle.id = schedule.shuttle_id and schedule.date_start =:travelDate)")
	List<BusResponseForDropDown> findBusAvailableInBrandByTravelDate(Integer brandId, LocalDate travelDate);
}
