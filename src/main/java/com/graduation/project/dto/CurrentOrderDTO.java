package com.graduation.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentOrderDTO {

	private Integer id;
	private List<String> listSeat;
	private LocalDateTime orderDate;
	private String brandName;
	private String orderStatus;
	private Double totalPrice;
	private Double deposit;
	private String paymentStatus;
	private String orderCode;
	private LocalDate travelDate;
	private LocalTime travelTime;
}
