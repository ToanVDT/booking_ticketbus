package com.graduation.project.service;

import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;

public interface DropOffService {

	public APIResponse updateDropOff(DropOffRequest dropOffRequest);
	public APIResponse removeDropOff(Integer id, Integer shuttleId);
	public APIResponse getAllDropOff(Integer shuttleId);
}
