package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.RouteRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.RouteResponse;

public interface RouteService {

	public APIResponse saveRoute(RouteRequest routeRequest);
	public APIResponse getRoute(Integer id);
	public List<RouteResponse> getAllRoute(Integer userId);
	public APIResponse removeRoute(Integer id, Integer userId);
}
