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
	
	@Query(nativeQuery = true, value = "SELECT bus.id, bus.name FROM bus WHERE bus.brand_id =:brandId")
	List<BusResponseForDropDown> findBusDropDown(Integer brandId);
	
	@Query(nativeQuery = true, value = "SELECT bus.id, bus.name FROM bus WHERE bus.brand_id =:brandId AND bus.id NOT IN (SELECT bus_id FROM bus, shuttle, schedule WHERE bus.id = schedule.bus_id AND shuttle.id = schedule.shuttle_id AND schedule.date_start =:travelDate)")
	List<BusResponseForDropDown> findBusAvailableInBrandByTravelDateForUpdate(Integer brandId, LocalDate travelDate);
	
	@Query(nativeQuery = true, value = "SELECT bus.id, bus.name FROM bus WHERE bus.brand_id =:brandId AND bus.id NOT IN (SELECT bus_id FROM bus, shuttle, schedule WHERE bus.id = schedule.bus_id AND shuttle.id = schedule.shuttle_id AND schedule.date_start BETWEEN :dateStart AND :dateEnd)")
	List<BusResponseForDropDown> findBusAvailableInBrandByTravelDate(Integer brandId, LocalDate dateStart,LocalDate dateEnd);
	
	Bus findByName(String name);
	
	Bus findByIdentityCode(String identityCode);
}
