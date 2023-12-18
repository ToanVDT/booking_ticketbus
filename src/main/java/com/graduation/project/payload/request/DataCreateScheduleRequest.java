package com.graduation.project.payload.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCreateScheduleRequest {

	private Integer id;
	private Integer shuttleReturnId;
	private Integer busId;
	private Integer shuttleId;
	private List<LocalDate> listDateStart;
	private List<LocalDate> listDateStartReturn;
	private Double price;
	private Double eatingFee;
}
