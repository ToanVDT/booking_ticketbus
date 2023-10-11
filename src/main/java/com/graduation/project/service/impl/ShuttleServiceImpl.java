package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.ShuttleMapper;
import com.graduation.project.dto.ShuttleDTO;
import com.graduation.project.entity.Bus;
import com.graduation.project.entity.Route;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ShuttleResponse;
import com.graduation.project.repository.BusRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.ShuttleService;

@Service
public class ShuttleServiceImpl implements ShuttleService{

	@Autowired
	private ShuttleRepository shuttleRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public APIResponse createShuttle(ShuttleRequest shuttleRequest) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = new Shuttle();
		Route route = routeRepository.findById(shuttleRequest.getRouteId()).orElse(null);
		shuttle.setRoute(route);
		Bus bus = busRepository.findById(shuttleRequest.getBusId()).orElse(null);
		shuttle.setBus(bus);
		shuttle.setStartTime(shuttleRequest.getStartTime());
		shuttle.setEndTime(shuttleRequest.getEndTime());
		shuttleRepository.save(shuttle);
		for(int i = 0; i < bus.getSeats(); i++) {
			Seat seat = new Seat();
			seat.setShuttle(shuttle);
			seat.setBooked(false);
			seat.setPrice(shuttleRequest.getPrice());
			if(i < bus.getSeats()/2) {
				seat.setName("A" + (i+1));
			}
			else {
				seat.setName("B" + (i-bus.getSeats()/2 + 1));
			}
			seatRepository.save(seat);
		}
		ShuttleMapper mapper = new ShuttleMapper();
		ShuttleDTO dto = mapper.toDTO(shuttle);
		response.setData(dto);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse updateShuttle(ShuttleRequest shuttleRequest) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = shuttleRepository.findById(shuttleRequest.getId()).orElse(null);
		Route route = routeRepository.findById(shuttleRequest.getRouteId()).orElse(null);
		shuttle.setRoute(route);
		Bus bus = busRepository.findById(shuttleRequest.getBusId()).orElse(null);
		shuttle.setBus(bus);
		shuttle.setStartTime(shuttleRequest.getStartTime());
		shuttle.setEndTime(shuttleRequest.getEndTime());
		shuttleRepository.save(shuttle);
		List<Seat> seats = seatRepository.findByShuttle(shuttle);
		for(Seat seat :seats) {
			seat.setPrice(shuttleRequest.getPrice());
			seatRepository.save(seat);
		}
		ShuttleMapper mapper = new ShuttleMapper();
		ShuttleDTO dto = mapper.toDTO(shuttle);
		response.setData(dto);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllShuttle(Integer routeId) {
		APIResponse response = new APIResponse();
		List<ShuttleResponse> shuttleResponses = shuttleRepository.findShuttle(routeId);
		response.setData(shuttleResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeShuttle(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
