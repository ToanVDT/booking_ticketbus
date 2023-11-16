package com.graduation.project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.dto.ImageDTO;

public interface ImageService {

	List<String> saveImage(MultipartFile[] images, Integer busId);
	
	List<ImageDTO> getImageByBus(Integer busId);

	void changeBusImage(MultipartFile[] images, Integer imageId);

	void removeImage(Integer imageId);
}
