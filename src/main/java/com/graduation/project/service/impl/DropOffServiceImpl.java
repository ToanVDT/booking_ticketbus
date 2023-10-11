package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.DropOff;
import com.graduation.project.entity.Route;
import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.DropOffResponse;
import com.graduation.project.repository.DropOffRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.service.DropOffService;

@Service
public class DropOffServiceImpl implements DropOffService{

	@Autowired
	private DropOffRepository dropOffRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Override
	public APIResponse saveDropOff(DropOffRequest dropOffRequest) {
		APIResponse response = new APIResponse();
		DropOff dropOff = null;
		if(dropOffRequest.getId() == null) {
			dropOff = new DropOff();
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		}
		else {
			dropOff = dropOffRepository.findById(dropOffRequest.getId()).orElse(null);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		}
		Route route = routeRepository.findById(dropOffRequest.getRouteId()).orElse(null);
		dropOff.setRoute(route);
		dropOff.setDropOffPoint(dropOffRequest.getDropOffPoint());
		dropOff.setDropOffTime(dropOffRequest.getDropOffTime());
		dropOffRepository.save(dropOff);
		getAllDropOff(dropOffRequest.getRouteId());
		response.setData(dropOff);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeDropOff(Integer id, Integer routeId) {
		APIResponse response = new APIResponse();
		dropOffRepository.deleteById(id);
		getAllDropOff(routeId);
//		response.setData(response);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllDropOff(Integer routeId) {
		APIResponse response = new APIResponse();
		List<DropOffResponse> dropOffResponses = dropOffRepository.findAllDropOff(routeId);
		response.setData(dropOffResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
