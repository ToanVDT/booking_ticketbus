package com.graduation.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.config.BrandMapper;
import com.graduation.project.dto.BrandDTO;
import com.graduation.project.dto.FileUploadDto;
import com.graduation.project.entity.Brand;
import com.graduation.project.payload.request.BrandRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.BrandResponse;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.service.BrandService;
import com.graduation.project.service.FileService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private FileService storageService;

	@Override
	public APIResponse saveBrand(BrandRequest brandRequest, MultipartFile[] files) {
		APIResponse response = new APIResponse();
		Brand brand = null;
		if(brandRequest.getId() == null) {
			brand = new Brand();
			response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		}
		else {
			brand = brandRepository.findById(brandRequest.getId()).orElse(null);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
		}
		brand.setAddress(brandRequest.getAddress());
		brand.setBrandName(brandRequest.getName());
		brand.setDescription(brandRequest.getDescription());
		if (files != null) {
			List<FileUploadDto> imgResult = storageService.uploadFiles(files);
			brand.setImage(imgResult.get(0).getFileUrl());			
		}
		brand.setPhoneBrand(brandRequest.getPhone());
		brand.setUserId(brandRequest.getUserId());
		brandRepository.save(brand);
		getBrandByUserId(brandRequest.getUserId());
		BrandMapper mapper = new BrandMapper();
		BrandDTO dto = mapper.toDTO(brand);
		response.setData(dto);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getBrand(Integer id) {
		APIResponse response = new APIResponse();
		Brand brand = brandRepository.findById(id).orElse(null);
		BrandMapper mapper = new BrandMapper();
		BrandDTO dto = mapper.toDTO(brand);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setData(dto);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getAllBrand() {
		APIResponse response = new APIResponse();
		List<BrandResponse> brandResponses = brandRepository.findAllBrand();
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setData(brandResponses);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse getBrandByUserId(Integer userId) {
		APIResponse response = new APIResponse();
		Brand brand = brandRepository.findByUserId(userId);
		BrandMapper mapper = new BrandMapper();
		BrandDTO dto = mapper.toDTO(brand);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setData(dto);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Boolean getBrandByBrandName(String name,Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		if(brand == null || (brand!=null && brand.getBrandName().equals(name))) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean getBrandByPhone(String phoneBrand,Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		if(brand == null || (brand!=null && brand.getPhoneBrand().equals(phoneBrand))){
			return false;
		}
		return true;
	}
}
