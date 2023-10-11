package com.graduation.project.service;

import com.graduation.project.payload.request.RouteRequest;
import com.graduation.project.payload.response.APIResponse;

public interface RouteService {

	public APIResponse saveRoute(RouteRequest routeRequest);
	public APIResponse getRoute(Integer id);
	public APIResponse getAllRoute(Integer brandId);
	public APIResponse removeRoute(Integer id, Integer brandId);
}
