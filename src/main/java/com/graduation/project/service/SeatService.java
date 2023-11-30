package com.graduation.project.service;

import java.time.LocalDate;
import java.util.List;

import com.graduation.project.dto.SeatDTO;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SeatEmptyResponse;

public interface SeatService {

	APIResponse getSeatInShuttle(Integer shuttleId);

	SeatEmptyResponse getSeatEmpty(LocalDate dateStart, Integer scheduleId);

	List<SeatDTO> getSeatWithScheduleId(Integer scheduleId);
	
	List<SeatDTO> getSeatWithStatus(Integer scheduleId, String status);
}
