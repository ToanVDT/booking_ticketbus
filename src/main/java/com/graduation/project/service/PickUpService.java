package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;

public interface PickUpService {

	APIResponse updatePickUp(PickUpRequest pickUpRequest);

	APIResponse removePickUp(Integer shuttleId);

	APIResponse getAllPickUp(Integer shuttleId);

	 APIResponse addPickUps(Integer shuttleId, List<PickUpRequest> requests);
}
