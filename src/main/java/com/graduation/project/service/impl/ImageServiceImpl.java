package com.graduation.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.dto.FileUploadDto;
import com.graduation.project.dto.ImageDTO;
import com.graduation.project.entity.Bus;
import com.graduation.project.entity.Image;
import com.graduation.project.repository.BusRepository;
import com.graduation.project.repository.ImageRepository;
import com.graduation.project.service.FileService;
import com.graduation.project.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private FileService storageService;
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<String> saveImage(MultipartFile[] images, Integer busId) {
		List<Image> busImg = new ArrayList<>();
		Image img = null;
		Bus bus = busRepository.findById(busId).orElse(null);
		if (images != null) {
			List<FileUploadDto> imgResult = storageService.uploadFiles(images);
			for (FileUploadDto item : imgResult) {
				img = new Image();
				img.setCreateAt(new Date());
				img.setImageName(item.getFileName());
				img.setImageURL(item.getFileUrl());
				img.setBus(bus);
				busImg.add(img);
			}
		}
		busImg = imageRepository.saveAll(busImg);
		List<String> result = new ArrayList<>();
		for (Image item : busImg) {
			result.add(item.getImageURL());
		}
		return result;
	}
	private List<ImageDTO> getBusImages(Bus bus) {
		List<ImageDTO> result = new ArrayList<>();
		ImageDTO dto = null;
		List<Image> images = bus.getImages();
		for (Image img : images) {
			dto = new ImageDTO();
			dto.setImageId(img.getId());
			dto.setImgName(img.getImageName());
			dto.setImgUrl(img.getImageURL());
			result.add(dto);
		}
		return result;
	}
	
	@Override
	public List<ImageDTO> getImageByBus(Integer busId) {
		Bus bus = busRepository.findById(busId).orElse(null);
		return getBusImages(bus);
	}

	@Override
	public void removeImage(Integer imageId) {
		Image image = imageRepository.findById(imageId).orElse(null);
		imageRepository.delete(image);
	}

	@Override
	public void changeBusImage(MultipartFile[] images, Integer imageId) {
		List<FileUploadDto> imgResult = storageService.uploadFiles(images);
		Image img = imageRepository.findById(imageId).orElse(null);
		img.setImageURL(imgResult.get(0).getFileUrl());
		imageRepository.save(img);
	}

}
