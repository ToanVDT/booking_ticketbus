package com.graduation.project.service;

import com.graduation.project.payload.request.ParkingRequest;
import com.graduation.project.payload.request.SearchShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequestUpdate;
import com.graduation.project.payload.response.APIResponse;

public interface ShuttleService {

	public APIResponse createShuttle(ShuttleRequest shuttleRequest);
	public APIResponse updateShuttle(ShuttleRequestUpdate shuttleRequest);
	public APIResponse getAllShuttle(Integer userId);
	public APIResponse getShuttleByRoute(Integer routeId);
	public APIResponse removeShuttle(Integer id);
	public APIResponse searchShuttle(SearchShuttleRequest request);
	public APIResponse createParkings(ParkingRequest requests);
}
