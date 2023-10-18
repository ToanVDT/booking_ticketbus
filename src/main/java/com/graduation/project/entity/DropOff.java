package com.graduation.project.entity;


import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DropOff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name ="dropoff_point")
	private String dropOffPoint;
	
	@Column(name ="dropoff_time")
	private LocalTime dropOffTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="shuttleId", nullable = false)
	private Shuttle shuttle;
}
