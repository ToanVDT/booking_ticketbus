package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.response.ShuttleResponse;

public interface ShuttleRepository extends JpaRepository<Shuttle, Integer>{

	@Query(nativeQuery = true, value = "SELECT distinct sh.start_time as startTime, sh.end_time as endTime, b.name as busName, b.seats as seats, s.price as price FROM shuttle sh, seat s, bus b WHERE sh.bus_id = b.id and s.shuttle_id = sh.id and sh.route_id =:routeId")
	List<ShuttleResponse> findShuttle(Integer routeId);
}
