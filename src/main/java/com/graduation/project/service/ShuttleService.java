package com.graduation.project.service;

import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.response.APIResponse;

public interface ShuttleService {

	public APIResponse createShuttle(ShuttleRequest shuttleRequest);
	public APIResponse updateShuttle(ShuttleRequest shuttleRequest);
	public APIResponse getAllShuttle(Integer routeId);
	public APIResponse removeShuttle(Integer id);
}
