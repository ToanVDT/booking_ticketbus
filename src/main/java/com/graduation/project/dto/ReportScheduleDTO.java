package com.graduation.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportScheduleDTO {

	private String orderCode;
	private LocalDateTime orderDate;
	private String customerName;
	private String customerPhone;
	private Double totalPrice;
	private String paymentMethod;
}
