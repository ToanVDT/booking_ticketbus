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
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "start_point")
	private String startPoint;
	
	@Column(name = "end_point")
	private String endPoint;
	
	@Column(name = "duration")
	private Double duration;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId",nullable = false)
	private Brand brand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
	private List<Shuttle> shuttles;
}
