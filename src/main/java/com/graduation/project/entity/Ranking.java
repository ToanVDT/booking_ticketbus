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
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name ="rank_name")
	private String rankName;
	
	@Column(name ="money_reduced")
	private Double moneyReduced;
	
	@JsonIgnore
	@OneToMany(mappedBy = "rank", fetch = FetchType.LAZY)
	private List<User> users;
	
	@JsonIgnore
	@OneToMany(mappedBy = "rank", fetch = FetchType.LAZY)
	private List<GiftCode> giftCodes;
	
}
