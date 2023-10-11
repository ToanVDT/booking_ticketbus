package com.graduation.project.entity;


import java.util.Date;

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
	private Date dropOffTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="routeId", nullable = false)
	private Route route;
}
