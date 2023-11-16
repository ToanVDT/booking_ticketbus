package com.graduation.project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.graduation.project.dto.FileUploadDto;

public interface FileService {
	 List<FileUploadDto> uploadFiles(MultipartFile[] files);
}
