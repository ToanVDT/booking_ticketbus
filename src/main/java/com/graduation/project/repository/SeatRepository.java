package com.graduation.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.payload.response.SeatEmptyResponse;
import com.graduation.project.payload.response.SeatResponseForCustomer;
import com.graduation.project.payload.response.SeatResponseForTicketPage;

public interface SeatRepository extends JpaRepository<Seat, Integer>{

	List<Seat> findBySchedule(Schedule schedule);
	
	@Query(nativeQuery = true, value = "SELECT * FROM seat WHERE seat.schedule_id=:scheduleId LIMIT 1")
	Seat findSeatByScheduleId(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "SELECT seat.id as id, if(status.status = 'INITIALIZED',0,1 ) as status, seat.name as seatName, price as price FROM  seat, status WHERE seat.status_id = status.id and seat.schedule_id =:scheduleId")
	List<SeatResponseForCustomer> findSeatsBySchedule(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "select seat.* from seat,ticket where seat.id = ticket.seat_id and ticket.id =:ticketId")
	Seat findByTicket(Integer ticketId);
	
	@Query(nativeQuery = true, value = "SELECT distinct seat.name AS seatName, seat.price AS price, seat.eating_fee AS eatingFee, status.status AS statusTicket, CONCAT(user.last_name, ' ', user.first_name) AS customerName, user.phone_number AS customerPhone FROM seat, status, ticket, orders, user WHERE ticket.order_id = orders.id AND orders.user_id = user.id AND seat.status_id = status.id AND seat.schedule_id =:scheduleId")
	List<SeatResponseForTicketPage> findSeatForTicketPage(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(seat.id) AS seatEmpty FROM seat, status, schedule WHERE seat.status_id = status.id AND status.status = 'INITIALIZED' and seat.schedule_id = schedule.id and schedule.date_start =:dateStart and schedule.id=:scheduleId")
	SeatEmptyResponse findSeatEmpty(LocalDate dateStart,Integer scheduleId);
}
