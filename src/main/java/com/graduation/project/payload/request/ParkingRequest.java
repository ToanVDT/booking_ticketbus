package com.graduation.project.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRequest {

	private Integer shuttleId;
	private List<DropOffRequest> dropOffs;
	private List<PickUpRequest> pickUps;
}
