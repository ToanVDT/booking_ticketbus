package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
