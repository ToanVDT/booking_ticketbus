package com.graduation.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.payload.request.BrandRequest;
import com.graduation.project.payload.response.APIResponse;

public interface BrandService {

	public APIResponse saveBrand(BrandRequest brandRequest,MultipartFile[] files);
	public APIResponse getBrand(Integer id);
	public APIResponse getAllBrand();
	public APIResponse getBrandByUserId(Integer userId);
	public Boolean getBrandByBrandName(String name,Integer userId);
	public Boolean getBrandByPhone(String phoneBrand,Integer userId);
}
