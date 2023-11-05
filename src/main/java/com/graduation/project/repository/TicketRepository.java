package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Ticket;

import jakarta.transaction.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from ticket where ticket.seat_id=:seatId")
	void deleteTicketWhenCancel(Integer seatId);
	
	@Query(nativeQuery = true, value = "select * from ticket where ticket.seat_id=:seatId and ticket.is_canceled = false")
	Ticket findBySeat(Integer seatId);
	
}
