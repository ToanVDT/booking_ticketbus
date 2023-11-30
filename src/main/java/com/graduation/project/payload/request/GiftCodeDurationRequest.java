package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCodeDurationRequest {

	private Integer id;
	private Integer durationCodeValid;
	private Double promotionMoney;
	private String prefix;
}
