package com.graduation.project.service;

import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;

public interface PickUpService {

	public APIResponse updatePickUp(PickUpRequest pickUpRequest);
	public APIResponse removePickUp(Integer id, Integer shuttleId);
	public APIResponse getAllPickUp(Integer shuttleId);
}
