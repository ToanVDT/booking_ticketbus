package com.graduation.project.entity;

import java.time.LocalTime;
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
public class Shuttle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "start_time")
	private LocalTime startTime;
	
	@Column(name = "end_time")
	private LocalTime endTime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="routeId", nullable = false)
	private Route route;
	
	@JsonIgnore
	@OneToMany(mappedBy = "shuttle", fetch = FetchType.LAZY)
	private List<PickUp> pickUps;
	
	@JsonIgnore
	@OneToMany(mappedBy = "shuttle", fetch = FetchType.LAZY)
	private List<Schedule> schedules;
	
	@JsonIgnore
	@OneToMany(mappedBy = "shuttle", fetch = FetchType.LAZY)
	private List<DropOff> dropOffs;
}
