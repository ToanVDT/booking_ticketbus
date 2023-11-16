package com.graduation.project.service;

import java.util.List;

import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.BusResponse;
import com.graduation.project.payload.response.BusResponseForDropDown;

public interface BusService {

	APIResponse saveBus(BusRequest busRequest);

	List<BusResponse> getAllBusInBrand(Integer brandId);

	APIResponse removeBus(Integer id);

	APIResponse getBus(Integer id);

	List<BusResponseForDropDown> getBusForDropDown(Integer userId);

	List<BusResponseForDropDown> getBusAvailableByTravelDate(Integer userId, java.time.LocalDate travelDate);

	Boolean checkDuplicateBusName(String name);

	Boolean checkDuplicateIdentityCode(String identityCode);
}
