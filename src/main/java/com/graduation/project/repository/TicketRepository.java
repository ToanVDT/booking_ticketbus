package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
