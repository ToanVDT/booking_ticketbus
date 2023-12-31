package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.DropOff;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.DropOffResponse;
import com.graduation.project.repository.DropOffRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.DropOffService;

@Service
public class DropOffServiceImpl implements DropOffService{

	@Autowired
	private DropOffRepository dropOffRepository;
	@Autowired
	private ShuttleRepository shuttleRepository;
	
	@Override
	public APIResponse updateDropOff(DropOffRequest dropOffRequest) {
		APIResponse response = new APIResponse();
		DropOff dropOff = dropOffRepository.findById(dropOffRequest.getDropOffId()).orElse(null);
		dropOff.setDropOffPoint(dropOffRequest.getDropOffPoint());
		dropOff.setDropOffTime(dropOffRequest.getDropOffTime());
		dropOffRepository.save(dropOff);
		response.setData(dropOff);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeDropOff(Integer shuttleId) {
		APIResponse response = new APIResponse();
		dropOffRepository.deleteDropOff(shuttleId);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllDropOff(Integer shuttleId) {
		APIResponse response = new APIResponse();
		List<DropOffResponse> dropOffResponses = dropOffRepository.findAllDropOff(shuttleId);
		response.setData(dropOffResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse addDropOffs(Integer shuttleId, List<DropOffRequest> requests) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = shuttleRepository.findById(shuttleId).orElse(null);
		for(DropOffRequest request:requests) {
			DropOff dropOff = new DropOff();
			dropOff.setDropOffPoint(request.getDropOffPoint());
			dropOff.setDropOffTime(request.getDropOffTime());
			dropOff.setShuttle(shuttle);
			dropOffRepository.save(dropOff);
		}
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
