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
public class OrderDTOForCustomerSearch {

	private List<String> listSeat;
	private LocalDateTime orderDate;
	private String orderCode;
	private String orderStatus;
	private Double totalPrice;
	private Double deposit;
	private String routeName;
	private String brandName;
	private LocalDate travelDate;
	private LocalTime startTime;
}
