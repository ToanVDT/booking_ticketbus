package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Ticket;
import com.graduation.project.payload.response.SeatEmptyResponse;
import com.graduation.project.payload.response.SeatResponseForCustomer;
import com.graduation.project.payload.response.SeatResponseForTicketPage;

public interface SeatRepository extends JpaRepository<Seat, Integer>{

	List<Seat> findBySchedule(Schedule schedule);
	
	@Query(nativeQuery = true, value = "SELECT * FROM seat WHERE seat.schedule_id=:scheduleId LIMIT 1")
	Seat findSeatByScheduleId(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "SELECT seat.id as id, status.status as status, seat.name as seatName, price as price FROM  seat, status WHERE seat.status_id = status.id and seat.schedule_id =:scheduleId")
	List<SeatResponseForCustomer> findSeatsBySchedule(Integer scheduleId);
	
	Seat findByTicket(Ticket ticket);
	
	@Query(nativeQuery = true, value = "SELECT distinct seat.name AS seatName, seat.price AS price, seat.eating_fee AS eatingFee, status.status AS statusTicket, CONCAT(user.last_name, ' ', user.first_name) AS customerName, user.phone_number AS customerPhone FROM seat, status, ticket, orders, user WHERE ticket.order_id = orders.id AND orders.user_id = user.id AND seat.status_id = status.id AND seat.schedule_id =:scheduleId")
	List<SeatResponseForTicketPage> findSeatForTicketPage(Integer scheduleId);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(seat.id) as seatEmpty FROM seat where schedule_id =:scheduleId and seat.id not in(select seat.id from status where seat.status_id = status.id and status.status = 'ORDERED')")
	SeatEmptyResponse findSeatEmpty(Integer scheduleId);

}
