package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;

public interface PickUpService {

	public APIResponse updatePickUp(PickUpRequest pickUpRequest);
	public APIResponse removePickUp(Integer shuttleId);
	public APIResponse getAllPickUp(Integer shuttleId);
	public APIResponse addPickUps(Integer shuttleId, List<PickUpRequest> requests);
}
