package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Shuttle;

public interface SeatRepository extends JpaRepository<Seat, Integer>{

	List<Seat> findByShuttle(Shuttle shuttle);
	@Query(nativeQuery = true, value = "SELECT * FROM seat WHERE seat.shutte_id=:shuttleId LIMIT 1")
	Seat findSeatByShuttleId(Integer shuttleId);
}
