package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
