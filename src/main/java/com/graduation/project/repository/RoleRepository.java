package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	@Query("FROM Role r WHERE r.name=:name")
	public Role findRoleByName(String name); 
}
