package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.ShuttleMapper;
import com.graduation.project.dto.ShuttleDTO;
import com.graduation.project.entity.Brand;
import com.graduation.project.entity.DropOff;
import com.graduation.project.entity.PickUp;
import com.graduation.project.entity.Route;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.request.DropOffRequest;
import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.request.SearchShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SearchShuttleResponse;
import com.graduation.project.payload.response.ShuttleResponse;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.DropOffRepository;
import com.graduation.project.repository.PickUpRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.ShuttleService;

@Service
public class ShuttleServiceImpl implements ShuttleService{

	@Autowired
	private ShuttleRepository shuttleRepository;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private PickUpRepository pickUpRepository;
	
	@Autowired
	private DropOffRepository dropOffRepository;
	
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public APIResponse createShuttle(ShuttleRequest shuttleRequest) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = new Shuttle();
		Route route = routeRepository.findById(shuttleRequest.getRouteId()).orElse(null);
		shuttle.setRoute(route);
		shuttle.setStartTime(shuttleRequest.getStartTime());
		shuttle.setTravelTime(shuttleRequest.getTravelTime());
		shuttleRepository.save(shuttle);
		for(DropOffRequest request:shuttleRequest.getDropOffs()) {
			DropOff dropOff = new DropOff();
			dropOff.setDropOffPoint(request.getDropOffPoint());
			dropOff.setDropOffTime(request.getDropOffTime());
			dropOff.setShuttle(shuttle);
			dropOffRepository.save(dropOff);
		}
		for(PickUpRequest request:shuttleRequest.getPickUps()) {
			PickUp pickUp = new PickUp();
			pickUp.setPickUpPoint(request.getPickUpPoint());
			pickUp.setPickUpTime(request.getPickUpTime());
			pickUp.setShuttle(shuttle);
			pickUpRepository.save(pickUp);
		}
		response.setData(shuttle);
		response.setSuccess(true);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		return response;
	}

	@Override
	public APIResponse getAllShuttle(Integer userId) {
		APIResponse response = new APIResponse();
		Brand brand = brandRepository.findByUserId(userId);
		List<ShuttleResponse> shuttleResponses = shuttleRepository.findShuttle(brand.getId());
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

	@Override
	public APIResponse updateShuttle(ShuttleRequest shuttleRequest) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = shuttleRepository.findById(shuttleRequest.getId()).orElse(null);
		Route route = routeRepository.findById(shuttleRequest.getRouteId()).orElse(null);
		shuttle.setRoute(route);
		shuttle.setStartTime(shuttleRequest.getStartTime());
		shuttle.setTravelTime(shuttleRequest.getTravelTime());
		shuttleRepository.save(shuttle);
		ShuttleMapper mapper = new ShuttleMapper();
		ShuttleDTO dto = mapper.toDTO(shuttle);
		response.setData(dto);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse searchShuttle(SearchShuttleRequest request) {
		APIResponse response = new APIResponse();
		List<SearchShuttleResponse> list = shuttleRepository.findShuttleAvailable(request.getStartPoint(), request.getEndPoint(), request.getStartTime());
		response.setData(list);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
