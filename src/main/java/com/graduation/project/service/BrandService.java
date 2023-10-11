package com.graduation.project.service;

import com.graduation.project.payload.request.BrandRequest;
import com.graduation.project.payload.response.APIResponse;

public interface BrandService {

	public APIResponse saveBrand(BrandRequest brandRequest);
	public APIResponse getBrand(Integer id);
	public APIResponse getAllBrand();
}
