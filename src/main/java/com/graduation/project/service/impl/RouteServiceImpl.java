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
		Brand brand = brandRepository.findById(routeRequest.getBrandId()).orElse(null);
		route.setBrand(brand);
		route.setStartPoint(routeRequest.getStartPoint());
		route.setEndPoint(routeRequest.getEndPoint());
		routeRepository.save(route);
		getAllRoute(routeRequest.getBrandId());
		response.setData(route);
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
	public APIResponse getAllRoute(Integer brandId) {
		APIResponse response = new APIResponse();
		List<RouteResponse> routeResponses = routeRepository.findAllRoute(brandId);
		response.setData(routeResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeRoute(Integer id, Integer brandId) {
		APIResponse response = new APIResponse();
		Route route = routeRepository.findById(id).orElse(null);
		if(!route.getDropOffs().isEmpty()) {
			response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
			response.setSuccess(false);
		}
		if(!route.getPickUps().isEmpty()) {
			response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
			response.setSuccess(false);
		}
		if(!route.getShuttles().isEmpty()) {
			response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
			response.setSuccess(false);
		}
		routeRepository.deleteById(id);
		getAllRoute(brandId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	
}
