package com.graduation.project.entity;

import java.time.LocalDateTime;
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
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name ="total_price")
	private Double totalPrice;
	
	@Column(name ="quantity_eating")
	private Integer quantityEating;
	
	@Column(name ="is_paid")
	private Boolean isPaid;
	
	@Column(name ="deposit")
	private Double deposit;
	
	@Column(name = "order_code")
	private String orderCode;
	
	@Column(name = "pickup_point")
	private String pickupPoint;
	
	@Column(name = "dropoff_point")
	private String dropoffPoint;
	
	@Column(name ="order_date")
	private LocalDateTime orderDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentId", nullable = false)
	private Payment payment;
	
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statusId", nullable = false)
	private Status status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<Ticket> tickets;
	
}
