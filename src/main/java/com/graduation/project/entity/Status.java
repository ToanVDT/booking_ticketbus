package com.graduation.project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "status")
	private String status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Seat> seats;
}
