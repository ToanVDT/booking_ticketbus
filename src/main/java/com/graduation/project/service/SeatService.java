package com.graduation.project.service;

import java.util.List;

import com.graduation.project.dto.SeatDTO;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SeatEmptyResponse;

public interface SeatService {

	public APIResponse getSeatInShuttle(Integer shuttleId);

	public SeatEmptyResponse getSeatEmpty(Integer scheduleId);

	public List<SeatDTO> getSeatWithScheduleId(Integer scheduleId);
}
