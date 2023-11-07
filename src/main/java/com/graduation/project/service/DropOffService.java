package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;

public interface DropOffService {

	public APIResponse updateDropOff(DropOffRequest dropOffRequest);
	public APIResponse removeDropOff(Integer shuttleId);
	public APIResponse getAllDropOff(Integer shuttleId);
	public APIResponse addDropOffs(Integer shuttleId, List<DropOffRequest> requests);
}
