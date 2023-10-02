package com.graduation.project.config;

import com.graduation.project.dto.AnonymousDTO;
import com.graduation.project.entity.User;

public class AnonymousMapper {

	public AnonymousDTO toDTO(User user) {
		AnonymousDTO dto = new AnonymousDTO();
		dto.setEmail(user.getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		return dto;
	}
}
