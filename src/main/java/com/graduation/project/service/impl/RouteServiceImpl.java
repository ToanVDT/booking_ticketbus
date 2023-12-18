package com.graduation.project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.RoutePopularDTO;
import com.graduation.project.entity.Brand;
import com.graduation.project.entity.Route;
import com.graduation.project.payload.request.RouteRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.RouteResponse;
import com.graduation.project.payload.response.RouteResponseForDropDown;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public APIResponse saveRoute(RouteRequest routeRequest) {
		APIResponse response = new APIResponse();
		Route route = null;
		try {
			Brand brand = brandRepository.findByUserId(routeRequest.getUserId());
//			route = routeRepository.findExistsRoute(routeRequest.getStartPoint(), routeRequest.getEndPoint(), brand.getId());
//			if(route != null) {
//				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
//				response.setSuccess(false);
//				return response;
//			}
//			else {
			if (routeRequest.getId() == null) {
				route = new Route();
				response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
			} else {
				route = routeRepository.findById(routeRequest.getId()).orElse(null);
				response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
			}
			route.setBrand(brand);
			route.setDuration(routeRequest.getDuration());
			route.setStartPoint(routeRequest.getStartPoint());
			route.setEndPoint(routeRequest.getEndPoint());
			routeRepository.save(route);
			List<RouteResponse> responses = getAllRoute(routeRequest.getUserId());
			response.setData(responses);
			response.setSuccess(true);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse getRoute(Integer id) {
		APIResponse response = new APIResponse();
		Route route = routeRepository.findById(id).orElse(null);
		response.setData(route);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public List<RouteResponse> getAllRoute(Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		List<RouteResponse> routeResponses = routeRepository.findAllRoute(brand.getId());
		return routeResponses;
	}

	@Override
	public APIResponse removeRoute(Integer id, Integer userId) {
		APIResponse response = new APIResponse();
		Route route = routeRepository.findById(id).orElse(null);
		if (!route.getShuttles().isEmpty()) {
			response.setMessage(ConstraintMSG.ERROR_DELETE_ROUTE_SHUTTLE);
			response.setSuccess(false);
			return response;
		}
		routeRepository.deleteById(id);
		List<RouteResponse> responses = getAllRoute(userId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		response.setData(responses);
		return response;
	}

	@Override
	public List<RouteResponseForDropDown> getListRouteDropDown(Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		List<RouteResponseForDropDown> list = routeRepository.findRouteCustomName(brand.getId());
		return list;
	}

	@Override
	public List<String> getAllStartPoint() {
		List<Route> list = routeRepository.findAll();
		List<String> listStartPoint = new ArrayList<>();
		String startPoint = null;
		for (Route route : list) {
			startPoint = new String();
			startPoint = route.getStartPoint();
			if (!listStartPoint.contains(startPoint)) {
				listStartPoint.add(startPoint);
			}
		}
		return listStartPoint;
	}

	@Override
	public List<String> getAllEndPoint() {
		List<Route> list = routeRepository.findAll();
		List<String> listEndPoint = new ArrayList<>();
		String endPoint = null;
		for (Route route : list) {
			endPoint = new String();
			endPoint = route.getEndPoint();
			if (!listEndPoint.contains(endPoint)) {
				listEndPoint.add(endPoint);
			}
		}
		return listEndPoint;
	}

	@Override
	public Set<String> getRouteToSearch() {
		List<String> listStartPoint = getAllStartPoint();
		List<String> listEndPoint = getAllEndPoint();
		Set<String> unionSet = new HashSet<String>(listStartPoint);
		unionSet.addAll(listEndPoint);
		return unionSet;
	}

	@Override
	public Set<RoutePopularDTO> getAllRouteToShow() {
		Set<RoutePopularDTO> set = new HashSet<>();
		Set<String> setTemp = new HashSet<>();
		List<Route> list = routeRepository.findAll();
		RoutePopularDTO dto = null;
		String route = null;
		String routeReverse = null;
		for (Route routes : list) {
			dto = new RoutePopularDTO();
			route = routes.getStartPoint() + " - " + routes.getEndPoint();
			routeReverse = routes.getEndPoint() + " - " + routes.getStartPoint();
			if (!setTemp.contains(routeReverse) && !setTemp.contains(route)) {
				dto.setRouteName(route);
				dto.setUrlImage(routes.getBrand().getImage());
				set.add(dto);
				setTemp.add(route);
			}
		}
		return set;
	}

	@Override
	public APIResponse createRoute(RouteRequest request) {
		APIResponse response = new APIResponse();
		Route route = null;
		try {
			Brand brand = brandRepository.findByUserId(request.getUserId());
			route = routeRepository.findExistsRoute(request.getStartPoint(), request.getEndPoint(),brand.getId());
			if (route != null) {
				response.setMessage(ConstraintMSG.DUPLICATE_DATA_MSG);
				response.setSuccess(false);
				return response;
			} else {
				route = new Route();
				Route route1 = new Route();
				route.setBrand(brand);
				route.setDuration(request.getDuration());
				route.setStartPoint(request.getStartPoint());
				route.setEndPoint(request.getEndPoint());
				routeRepository.save(route);
				route1.setBrand(brand);
				route1.setDuration(request.getDuration());
				route1.setStartPoint(request.getEndPoint());
				route1.setEndPoint(request.getStartPoint());
				routeRepository.save(route1);
				List<RouteResponse> responses = getAllRoute(request.getUserId());
				response.setData(responses);
				response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
				response.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse updateRoute(RouteRequest request) {
		APIResponse response = new APIResponse();
		Route route = routeRepository.findById(request.getId()).orElse(null);
		route.setDuration(request.getDuration());
		route.setStartPoint(request.getStartPoint());
		route.setEndPoint(request.getEndPoint());
		routeRepository.save(route);
		List<RouteResponse> responses = getAllRoute(request.getUserId());
		response.setData(responses);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Route getReturnRoute(Integer routeId) {
		Route route = routeRepository.findById(routeId).orElse(null);
		String startPointReturn = route.getEndPoint();
		String endPointReturn  = route.getStartPoint();
		Integer brandId = route.getBrand().getId();
		Route returnRoute = routeRepository.findExistsRoute(startPointReturn, endPointReturn, brandId);
		return returnRoute;
	}
}
