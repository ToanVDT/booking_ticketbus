package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{

}
