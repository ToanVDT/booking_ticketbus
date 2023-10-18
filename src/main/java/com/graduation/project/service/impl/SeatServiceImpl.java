package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SeatResponse;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	private SeatRepository seatRepository;

	@Override
	public APIResponse getSeatInShuttle(Integer scheduleId) {
		APIResponse response = new APIResponse();
		List<SeatResponse> responses = seatRepository.findSeatInSchedule(scheduleId);
		response.setData(responses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
