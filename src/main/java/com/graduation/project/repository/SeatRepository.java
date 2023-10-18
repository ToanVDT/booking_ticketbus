package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.payload.response.SeatResponse;

public interface SeatRepository extends JpaRepository<Seat, Integer>{

	List<Seat> findBySchedule(Schedule schedule);
	
	@Query(nativeQuery = true, value = "SELECT * FROM seat WHERE seat.schedule_id=:scheduleId LIMIT 1")
	Seat findSeatByScheduleId(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "SELECT seat.id as id, seat.booked as booked, seat.price as price, seat.name as setName FROM seat WHERE seat.schedule_id =:scheduleId")
	List<SeatResponse> findSeatInSchedule(Integer scheduleId);
}
