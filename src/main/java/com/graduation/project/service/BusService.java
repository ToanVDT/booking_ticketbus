package com.graduation.project.service;

import com.graduation.project.payload.request.BusRequest;
import com.graduation.project.payload.response.APIResponse;

public interface BusService {

	public APIResponse saveBus(BusRequest busRequest);
	public APIResponse getAllBusInBrand(Integer brandId);
	public APIResponse removeBus(Integer id,Integer brandId);
	public APIResponse getBus(Integer id);
}
