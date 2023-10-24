package com.graduation.project.payload.request;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleRequest {

	private Integer id;
	private Integer routeId;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<DropOffRequest> dropOffs;
	private List<PickUpRequest> pickUps;
}
