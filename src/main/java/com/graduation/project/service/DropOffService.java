package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;

public interface DropOffService {

	APIResponse updateDropOff(DropOffRequest dropOffRequest);

	APIResponse removeDropOff(Integer shuttleId);

	APIResponse getAllDropOff(Integer shuttleId);

	APIResponse addDropOffs(Integer shuttleId, List<DropOffRequest> requests);
}
