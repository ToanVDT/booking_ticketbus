package com.graduation.project.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	private List<Integer> seatId;
	private Integer scheduleId;
	private String pickUp;
	private String dropOff;
	private CustomerBookingRequest customer;
	private String giftCode;
	private Integer paymentId;
	private Double paidAmount;
	private Double eatingFee;
}
