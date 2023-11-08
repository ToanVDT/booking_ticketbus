package com.graduation.project.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCodeDTO {

	private String giftCode;
	private LocalDate expireDate;
	private Boolean isUsed;
	private Double promotion;
}
