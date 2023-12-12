package com.graduation.project.payload.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailSendInformOrderToBrandOwnerRequest {

	private String customerName;
	private String brandName;
	private String customerPhone;
	private String customerEmail;
	private String routeName;
	private String pickUpPoint;
	private String dropOffPoint;
	private List<String> listSeatOrderd;
	private Double totalPrice;
	private String orderStatus;
	private String paymentStatus;
	private LocalDateTime bookingDate;
	private String dateTimeTravel;
}
