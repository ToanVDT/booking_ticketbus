package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.ImageDTO;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.ImageService;

@RestController
@RequestMapping()
public class ImageController {
	
	@Autowired
	private ImageService imageService;

	@PostMapping("/image/{busId}")
	public ResponseEntity<APIResponse> saveImage(@RequestParam("file") MultipartFile[] files, @PathVariable("busId") Integer busId) {
		final List<String> result = imageService.saveImage(files, busId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG, result,true));
	}
	
	@GetMapping("/image/{busId}")
	public ResponseEntity<APIResponse> getImageByBus(@PathVariable("busId") Integer busId) {
		final List<ImageDTO> result = imageService.getImageByBus(busId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG, result,true));
	}
	
	@PutMapping("/image/{imgId}")
	public ResponseEntity<APIResponse> changeBusImage(@RequestParam("file") MultipartFile[] files, @PathVariable("imgId") Integer imgId) {
		imageService.changeBusImage(files, imgId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.UPDATE_DATA_MSG, null,true));
	}
	
	@DeleteMapping("/image/{imageId}")
	public ResponseEntity<APIResponse> removeImage(@PathVariable("imageId") Integer imageId) {
		imageService.removeImage(imageId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.DELETE_DATA_MSG, null,true));
	}
}
