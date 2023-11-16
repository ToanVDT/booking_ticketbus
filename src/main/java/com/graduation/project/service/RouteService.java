package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.RouteRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.RouteResponse;
import com.graduation.project.payload.response.RouteResponseForDropDown;

public interface RouteService {

	APIResponse saveRoute(RouteRequest routeRequest);

	APIResponse getRoute(Integer id);

	List<RouteResponse> getAllRoute(Integer userId);

	APIResponse removeRoute(Integer id, Integer userId);

	 List<RouteResponseForDropDown> getListRouteDropDown(Integer userId);
}
