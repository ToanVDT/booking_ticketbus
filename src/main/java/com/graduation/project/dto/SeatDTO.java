package com.graduation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

	private Integer id;
	private Double price;
	private Double eatingFee;
	private String seatName;
	private String statusTicket;
	private String customerName;
	private String customerPhone;
}
