package com.graduation.project.payload.request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropOffRequest {

	private Integer dropOffId;
	private String dropOffPoint;
	private LocalTime dropOffTime;
}
