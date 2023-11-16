package com.graduation.project.service;

import java.time.LocalDate;

import com.graduation.project.payload.request.ParkingRequest;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequestUpdate;
import com.graduation.project.payload.response.APIResponse;

public interface ShuttleService {

	APIResponse createShuttle(ShuttleRequest shuttleRequest);

	APIResponse updateShuttle(ShuttleRequestUpdate shuttleRequest);

	APIResponse getAllShuttle(Integer userId);

	APIResponse getShuttleByRoute(Integer routeId);

	APIResponse removeShuttle(Integer id);

	APIResponse searchShuttle(String startPoint, String endPoint, LocalDate travelDate);

	APIResponse createParkings(ParkingRequest requests);
}
