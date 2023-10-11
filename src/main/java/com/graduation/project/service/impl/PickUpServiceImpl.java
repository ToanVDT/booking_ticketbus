package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.PickUp;
import com.graduation.project.entity.Route;
import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.PickUpResponse;
import com.graduation.project.repository.PickUpRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.service.PickUpService;

@Service
public class PickUpServiceImpl implements PickUpService{

	@Autowired
	private PickUpRepository pickUpRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Override
	public APIResponse savePickUp(PickUpRequest pickUpRequest) {
		APIResponse response = new APIResponse();
		PickUp pickUp = null;
		if(pickUpRequest.getId() == null) {
			pickUp = new PickUp();
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		}
		else {
			pickUp = pickUpRepository.findById(pickUpRequest.getId()).orElse(null);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		}
		Route route = routeRepository.findById(pickUpRequest.getRouteId()).orElse(null);
		pickUp.setRoute(route);
		pickUp.setPickUpPoint(pickUpRequest.getPickUpPoint());
		pickUp.setPickUpTime(pickUpRequest.getPickUpTime());
		pickUpRepository.save(pickUp);
		getAllPickUp(pickUpRequest.getRouteId());
		response.setData(pickUp);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse rempvePickUp(Integer id, Integer routeId) {
		APIResponse response = new APIResponse();
		pickUpRepository.deleteById(id);
		getAllPickUp(routeId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllPickUp(Integer routeId) {
		APIResponse response = new APIResponse();
		List<PickUpResponse> pickUpResponses = pickUpRepository.findAllPickUp(routeId);
		response.setData(pickUpResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}
}
