package com.graduation.project.service.impl;

import java.time.LocalDate;
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
import com.graduation.project.payload.request.ParkingRequest;
import com.graduation.project.payload.request.PickUpRequest;
import com.graduation.project.payload.request.ShuttleRequest;
import com.graduation.project.payload.request.ShuttleRequestUpdate;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SearchShuttleResponse;
import com.graduation.project.payload.response.ShuttleResponse;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.DropOffRepository;
import com.graduation.project.repository.PickUpRepository;
import com.graduation.project.repository.RouteRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.service.DropOffService;
import com.graduation.project.service.PickUpService;
import com.graduation.project.service.ShuttleService;

@Service
public class ShuttleServiceImpl implements ShuttleService{

	@Autowired
	private ShuttleRepository shuttleRepository;
	
	@Autowired
	private PickUpService pickUpService;
	
	@Autowired
	private DropOffService dropOffService;

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
		shuttle.setEndTime(shuttleRequest.getEndTime());
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
		APIResponse response = new APIResponse();
		try {
			Shuttle shuttle = shuttleRepository.findById(id).orElse(null);
			if(!shuttle.getSchedules().isEmpty()) {
				response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
				response.setSuccess(false);
				return response;
			}
			else {
				dropOffService.removeDropOff(id);
				pickUpService.removePickUp(id);
				shuttleRepository.deleteById(id);
				response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
				response.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse updateShuttle(ShuttleRequestUpdate shuttleRequest) {
		APIResponse response = new APIResponse();
		Shuttle shuttle = shuttleRepository.findById(shuttleRequest.getId()).orElse(null);
		shuttle.setStartTime(shuttleRequest.getStartTime());
		shuttle.setEndTime(shuttleRequest.getEndTime());
		shuttleRepository.save(shuttle);
		ShuttleMapper mapper = new ShuttleMapper();
		ShuttleDTO dto = mapper.toDTO(shuttle);
		response.setData(dto);
		response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse searchShuttle(String startPoint,String endPoint, LocalDate travelDate) {
		APIResponse response = new APIResponse();
		List<SearchShuttleResponse> list = shuttleRepository.findShuttleAvailable(startPoint, endPoint, travelDate);
		response.setData(list);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getShuttleByRoute(Integer routeId) {
		APIResponse response = new APIResponse();
		List<Shuttle> shuttleResponses = shuttleRepository.findShuttleByRoute(routeId);
		response.setData(shuttleResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse createParkings(ParkingRequest requests) {
		APIResponse response = new APIResponse();
		try {
			Shuttle shuttle = shuttleRepository.findById(requests.getShuttleId()).orElse(null);
			for(DropOffRequest request:requests.getDropOffs()) {
				DropOff dropOff = new DropOff();
				dropOff.setDropOffPoint(request.getDropOffPoint());
				dropOff.setDropOffTime(request.getDropOffTime());
				dropOff.setShuttle(shuttle);
				dropOffRepository.save(dropOff);
			}
			for(PickUpRequest request:requests.getPickUps()) {
				PickUp pickUp = new PickUp();
				pickUp.setPickUpPoint(request.getPickUpPoint());
				pickUp.setPickUpTime(request.getPickUpTime());
				pickUp.setShuttle(shuttle);
				pickUpRepository.save(pickUp);
			}
			response.setData(shuttle);
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
			response.setSuccess(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
