package com.graduation.project.config;

import com.graduation.project.dto.BrandDTO;
import com.graduation.project.entity.Brand;

public class BrandMapper {

	public BrandDTO toDTO(Brand brand) {
		BrandDTO dto = new BrandDTO();
		dto.setAddress(brand.getAddress());
		dto.setDescription(brand.getDescription());
		dto.setImage(brand.getImage());
		dto.setName(brand.getBrandName());
		dto.setPhone(brand.getPhoneBrand());
		return dto;
	}
}
