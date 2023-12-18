package com.graduation.project.service.impl;

import java.time.LocalDate;
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
import com.graduation.project.payload.response.BusResponseForDropDown;
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
		try {			
			Bus bus = null;
			if(busRequest.getId() == null) {
				bus = new Bus();
				response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
			}
			else {
				bus = busRepository.findById(busRequest.getId()).orElse(null);
				response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
			}
			
			Brand brand = brandRepository.findByUserId(busRequest.getUserId());
			bus.setBrand(brand);
			BusType type = typeRepository.findById(busRequest.getTypeId()).orElse(null);
			bus.setType(type);
			bus.setDescription(busRequest.getDescription());
			bus.setIdentityCode(busRequest.getIdentityCode());
			bus.setSeats(busRequest.getSeats());
			bus.setName(busRequest.getName());
			busRepository.save(bus);
			List<BusResponse> busResponses= getAllBusInBrand(busRequest.getUserId());
//			BusMapper mapper = new BusMapper();
//			BusDTO dto = mapper.toDTO(bus);
			response.setData(busResponses);
			response.setSuccess(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<BusResponse> getAllBusInBrand(Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		List<BusResponse> busResponses = busRepository.findAllBus(brand.getId());
		return busResponses;
	}

	@Override
	public APIResponse removeBus(Integer id) {
		APIResponse response = new APIResponse();
		try {			
			Bus bus = busRepository.findById(id).orElse(null);
			if(!bus.getSchedules().isEmpty()) {
				response.setMessage(ConstraintMSG.ERROR_DELETE_MSG);
				response.setSuccess(false);
				return response;
			}
			busRepository.deleteById(id);
			response.setMessage(ConstraintMSG.DELETE_DATA_MSG);
			response.setSuccess(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public List<BusResponseForDropDown> getBusForDropDown(Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		List<BusResponseForDropDown> list = busRepository.findBusDropDown(brand.getId());
		System.out.println(list);
		return list;
	}

	@Override
	public List<BusResponseForDropDown> getBusAvailableByTravelDateForUpdate(Integer userId, LocalDate travelDate) {
		Brand brand = brandRepository.findByUserId(userId);
		List<BusResponseForDropDown> list = busRepository.findBusAvailableInBrandByTravelDateForUpdate(brand.getId(), travelDate);
		return list;
	}
	@Override
	public List<BusResponseForDropDown> getBusAvailableByTravelDate(Integer userId, LocalDate dateStart,LocalDate dateEnd) {
		Brand brand = brandRepository.findByUserId(userId);
		List<BusResponseForDropDown> list = busRepository.findBusAvailableInBrandByTravelDate(brand.getId(), dateStart,dateEnd);
		return list;
	}
	@Override
	public Boolean checkDuplicateBusName(String name) {
		Bus bus = busRepository.findByName(name);
		if(bus == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkDuplicateIdentityCode(String identityCode) {
		Bus bus = busRepository.findByIdentityCode(identityCode);
		if(bus == null) {
			return false;
		}
		return true;
	}
}
