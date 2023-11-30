package com.graduation.project.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class GiftCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "giftcode")
	private String giftCode;
	
	@Column(name = "expire_date")
	private LocalDate expireDate;
	
	@Column(name = "is_used")
	private Boolean isUsed;
	
	@Column(name ="user_id")
	private Integer userId;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="rankId", nullable = false)
	private Ranking rank; 

}
