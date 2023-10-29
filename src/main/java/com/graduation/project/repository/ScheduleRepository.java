package com.graduation.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.payload.response.ScheduleResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query(nativeQuery = true, value = "SELECT distinct schedule.id, schedule.date_start as dateStart, shuttle.start_time as startTime, bus.name as busName, bus.seats as seats, seat.price as price, seat.eating_fee as eatingFee FROM schedule, shuttle, bus, seat  WHERE schedule.shuttle_id = shuttle.id and schedule.bus_id = bus.id and schedule.id = seat.schedule_id and shuttle.route_id=:routeId ORDER BY schedule.date_start DESC")
	List<ScheduleResponse> findScheduleInRoute(Integer routeId);
	
	@Query(nativeQuery = true, value = "SELECT schedule.id as id, concat(route.start_point,'-',route.end_point) as routeName, shuttle.start_time as startTime FROM booking_ticket.schedule, shuttle, route where schedule.date_start =:dateStart and shuttle.id=schedule.shuttle_id and route.id = shuttle.route_id")
	List<ScheduleResponseForDropDown> findScheduleByTravelDate(LocalDate dateStart);
}
