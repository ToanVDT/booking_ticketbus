package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
