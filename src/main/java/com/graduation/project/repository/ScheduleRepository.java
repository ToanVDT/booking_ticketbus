package com.graduation.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.payload.response.ScheduleResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query(nativeQuery = true, value = "SELECT DISTINCT schedule.id, schedule.date_start AS dateStart, shuttle.start_time AS startTime, bus.name AS busName, bus.id AS busId, bus.seats AS seats, seat.price AS price, seat.eating_fee AS eatingFee, seat2.emptys AS emptySeats FROM schedule, shuttle, bus, seat JOIN (SELECT COUNT(id) AS emptys, seat.schedule_id FROM seat WHERE seat.status_id = 5 GROUP BY seat.schedule_id) AS seat2 ON seat.schedule_id = seat2.schedule_id WHERE schedule.shuttle_id = shuttle.id AND schedule.bus_id = bus.id AND schedule.id = seat.schedule_id AND shuttle.route_id =:routeId ORDER BY schedule.date_start DESC")
	List<ScheduleResponse> findScheduleInRoute(Integer routeId);
	
	@Query(nativeQuery = true, value = "SELECT schedule.id as id, concat(route.start_point,'-',route.end_point) as routeName, shuttle.start_time as startTime, shuttle.id as shuttleId FROM booking_ticket.schedule, shuttle, route where schedule.date_start =:dateStart and shuttle.id=schedule.shuttle_id and route.id = shuttle.route_id AND route.brand_id =:brandId")
	List<ScheduleResponseForDropDown> findScheduleByTravelDate(LocalDate dateStart, Integer brandId);
	
}
