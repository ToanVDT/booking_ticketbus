package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.Bus;
import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ScheduleResponse;
import com.graduation.project.repository.BusRepository;
import com.graduation.project.repository.ScheduleRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private ShuttleRepository shuttleRepository;
	
	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public APIResponse createSchedule(ScheduleRequest request) {
		APIResponse response  = new APIResponse();
		Schedule schedule = new Schedule();
		Bus bus = busRepository.findById(request.getBusId()).orElse(null);
		schedule.setBus(bus);
		Shuttle shuttle = shuttleRepository.findById(request.getShuttleId()).orElse(null);
		schedule.setShuttle(shuttle);
		schedule.setDateStart(request.getTravelDate());
		scheduleRepository.save(schedule);
		for (int i = 0; i < bus.getSeats(); i++) {
			Seat seat = new Seat();
			seat.setSchedule(schedule);
			seat.setBooked(false);
			seat.setPrice(request.getPrice());
			if (i < bus.getSeats() / 2) {
				seat.setName("A" + (i + 1));
			} else {
				seat.setName("B" + (i - bus.getSeats() / 2 + 1));
			}
			seatRepository.save(seat);
		}
		response.setData(schedule);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse updateSchedule(ScheduleRequest request) {
		APIResponse response  = new APIResponse();
		Schedule schedule = scheduleRepository.findById(request.getId()).orElse(null);
		Bus bus = busRepository.findById(request.getBusId()).orElse(null);
		schedule.setBus(bus);
		Shuttle shuttle = shuttleRepository.findById(request.getShuttleId()).orElse(null);
		schedule.setShuttle(shuttle);
		schedule.setDateStart(request.getTravelDate());
		scheduleRepository.save(schedule);
		List<Seat> seats = seatRepository.findBySchedule(schedule);
		for(Seat seat :seats) {
			seat.setPrice(request.getPrice());
			seatRepository.save(seat);
		}
		response.setData(schedule);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getSchedules(Integer routeId) {
		APIResponse response = new APIResponse();
		List<ScheduleResponse> scheduleResponses = scheduleRepository.findScheduleInRoute(routeId);
		response.setData(scheduleResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
