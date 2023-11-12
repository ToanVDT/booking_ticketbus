package com.graduation.project.payload.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailOrderStatusRequest {

	private String customerName;
	private String orderCode;
	private String brandName;
	private String routeName;
	private String pickUpPoint;
	private String dropOffPoint;
	private List<String> listSeatOrderd;
	private Double totalPrice;
	private String orderStatus;
	private String paymentStatus;
	private LocalDate bookingDate;
	private String dateTimeTravel;
	
}
