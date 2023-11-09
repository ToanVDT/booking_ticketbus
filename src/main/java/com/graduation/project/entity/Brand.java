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
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "brand_name")
	private String brandName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "phone_brand")
	private String phoneBrand;
	
	@Column(name = "image")
	private String image;
	
	@Column(name ="userId")
	private Integer userId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Bus> bus;
	
	@JsonIgnore
	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Route> routes;
	
}
