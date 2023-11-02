package com.graduation.project.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

	private Integer id;
	private List<String> listSeat;
	private LocalDateTime orderDate;
	private String orderCode;
	private String orderStatus;
	private Double totalPrice;
	private Double deposit;
	private String paymentStatus;
}
