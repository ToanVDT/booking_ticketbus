package com.graduation.project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "identity_code")
	private String identityCode;
		
	@Column(name = "seats")
	private Integer seats;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "images")
	private List<String> images;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId",nullable = false)
	private Brand brand;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId",nullable = false)
	private BusType type;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<Shuttle> shuttles;
}
