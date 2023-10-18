package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.payload.response.ScheduleResponse;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query(nativeQuery = true, value = "SELECT distinct schedule.date_start as dateStart, shuttle.start_time as startTime, bus.name as busName, bus.seats as seats, seat.price as price FROM schedule, shuttle, bus, seat  WHERE schedule.shuttle_id = shuttle.id and schedule.bus_id = bus.id and schedule.id = seat.schedule_id and shuttle.route_id=:routeId")
	List<ScheduleResponse> findScheduleInRoute(Integer routeId);
}
