package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.BusResponse;
import com.graduation.project.payload.response.BusResponseForDropDown;

public interface BusService {

	public APIResponse saveBus(BusRequest busRequest);
	public List<BusResponse> getAllBusInBrand(Integer brandId);
	public APIResponse removeBus(Integer id);
	public APIResponse getBus(Integer id);
	public List<BusResponseForDropDown> getBusForDropDown(Integer userId);
	public List<BusResponseForDropDown> getBusAvailableByTravelDate(Integer userId, java.time.LocalDate travelDate);
	public Boolean checkDuplicateBusName(String name);
	public Boolean checkDuplicateIdentityCode(String identityCode);
}
