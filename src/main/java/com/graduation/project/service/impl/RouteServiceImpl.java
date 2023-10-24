package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
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
public class RouteServiceImpl implements RouteService{

	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public APIResponse saveRoute(RouteRequest routeRequest) {
		APIResponse response = new APIResponse();
		Route route = null;
		if(routeRequest.getId() == null) {
			route = new Route();
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		}
		else {
			route = routeRepository.findById(routeRequest.getId()).orElse(null);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		}
		Brand brand = brandRepository.findByUserId(routeRequest.getUserId());
		route.setBrand(brand);
		route.setStartPoint(routeRequest.getStartPoint().replace("Tỉnh", "").trim());
		route.setEndPoint(routeRequest.getEndPoint().replace("Tỉnh", "").trim());
		routeRepository.save(route);
		List<RouteResponse>  responses=getAllRoute(routeRequest.getUserId());
		response.setData(responses);
		response.setSuccess(true);
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
		if(!route.getShuttles().isEmpty()) {
			response.setMessage(ConstraintMSG.ERROR_DELETE_ROUTE_SHUTTLE);
			response.setSuccess(false);
			return response;
		}
		routeRepository.deleteById(id);
		List<RouteResponse> responses =  getAllRoute(userId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		response.setData(responses);
		return response;
	}
	@Override
	public List<RouteResponseForDropDown> getListRouteDropDown(Integer userId){
		Brand brand = brandRepository.findByUserId(userId);
		List<RouteResponseForDropDown> list = routeRepository.findRouteCustomName(brand.getId());
		return list;
	}
	
}
