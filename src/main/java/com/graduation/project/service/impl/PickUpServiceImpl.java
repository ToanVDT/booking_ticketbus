package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.DropOff;
import com.graduation.project.entity.PickUp;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.PickUpResponse;
import com.graduation.project.repository.PickUpRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.PickUpService;

@Service
public class PickUpServiceImpl implements PickUpService{

	@Autowired
	private PickUpRepository pickUpRepository;
	
	@Autowired
	private ShuttleRepository shuttleRepository;
	
	@Override
	public APIResponse updatePickUp(PickUpRequest pickUpRequest) {
		APIResponse response = new APIResponse();
		PickUp pickUp =pickUpRepository.findById(pickUpRequest.getId()).orElse(null);
		pickUp.setPickUpPoint(pickUpRequest.getPickUpPoint());
		pickUp.setPickUpTime(pickUpRequest.getPickUpTime());
		pickUpRepository.save(pickUp);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setData(pickUp);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removePickUp(Integer id, Integer shuttleId) {
		APIResponse response = new APIResponse();
		pickUpRepository.deleteById(id);
		getAllPickUp(shuttleId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllPickUp(Integer shuttleId) {
		APIResponse response = new APIResponse();
		List<PickUpResponse> pickUpResponses = pickUpRepository.findAllPickUp(shuttleId);
		response.setData(pickUpResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse addPickUps(Integer shuttleId, List<PickUpRequest> requests) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = shuttleRepository.findById(shuttleId).orElse(null);
		for(PickUpRequest request:requests) {
			PickUp pickUp = new PickUp();
			pickUp.setPickUpPoint(request.getPickUpPoint());
			pickUp.setPickUpTime(request.getPickUpTime());
			pickUp.setShuttle(shuttle);
			pickUpRepository.save(pickUp);
		}
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}
}
