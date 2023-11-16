package com.graduation.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.payload.request.BrandRequest;
import com.graduation.project.payload.response.APIResponse;

public interface BrandService {

	APIResponse saveBrand(BrandRequest brandRequest, MultipartFile[] files);

	APIResponse getBrand(Integer id);

	APIResponse getAllBrand();

	APIResponse getBrandByUserId(Integer userId);

	Boolean getBrandByBrandName(String name, Integer userId);

	Boolean getBrandByPhone(String phoneBrand, Integer userId);
}
