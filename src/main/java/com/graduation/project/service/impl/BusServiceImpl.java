package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.BusMapper;
import com.graduation.project.dto.BusDTO;
import com.graduation.project.entity.Brand;
import com.graduation.project.entity.Bus;
import com.graduation.project.entity.BusType;
import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.BusResponse;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.BusRepository;
import com.graduation.project.repository.TypeRepository;
import com.graduation.project.service.BusService;

@Service
public class BusServiceImpl implements BusService{

	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	public APIResponse saveBus(BusRequest busRequest) {
		APIResponse response = new APIResponse();
		Bus bus = null;
		if(busRequest.getId() == null) {
			bus = new Bus();
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		}
		else {
			bus = busRepository.findById(busRequest.getId()).orElse(null);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		}
		Brand brand = brandRepository.findById(busRequest.getBrandId()).orElse(null);
		bus.setBrand(brand);
		BusType type = typeRepository.findById(busRequest.getTypeId()).orElse(null);
		bus.setType(type);
		bus.setDescription(busRequest.getDescription());
		bus.setIdentityCode(busRequest.getIdentityCode());
		bus.setSeats(busRequest.getSeats());
		bus.setName(busRequest.getName());
		busRepository.save(bus);
		getAllBusInBrand(busRequest.getBrandId());
		BusMapper mapper = new BusMapper();
		BusDTO dto = mapper.toDTO(bus);
		response.setData(dto);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllBusInBrand(Integer brandId) {
		APIResponse response = new APIResponse();
		List<BusResponse> busResponses = busRepository.findAllBus(brandId);
		response.setData(busResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse removeBus(Integer id, Integer brandId) {
		APIResponse response = new APIResponse();
		Bus bus = busRepository.findById(id).orElse(null);
		if(!bus.getShuttles().isEmpty()) {
//			response.setData(bus);
			response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
			response.setSuccess(false);
			return response;
		}
		busRepository.deleteById(id);
		getAllBusInBrand(brandId);
//		response.setData(bus);
		response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getBus(Integer id) {
		APIResponse response = new APIResponse();
		Bus bus = busRepository.findById(id).orElse(null);
		BusMapper mapper = new BusMapper();
		BusDTO dto = mapper.toDTO(bus);
		response.setData(dto);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

}
