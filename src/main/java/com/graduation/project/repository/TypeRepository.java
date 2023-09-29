package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.BusType;

public interface TypeRepository extends JpaRepository<BusType, Integer>{

}
