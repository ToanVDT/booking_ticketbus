package com.graduation.project.service;

import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;

public interface PickUpService {

	public APIResponse savePickUp(PickUpRequest pickUpRequest);
	public APIResponse rempvePickUp(Integer id, Integer routeId);
	public APIResponse getAllPickUp(Integer routeId);
}
