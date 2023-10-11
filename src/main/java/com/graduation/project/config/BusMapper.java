package com.graduation.project.config;

import com.graduation.project.dto.BusDTO;
import com.graduation.project.entity.Bus;

public class BusMapper {

	public BusDTO toDTO(Bus bus) {
		BusDTO dto = new BusDTO();
		dto.setBusType(bus.getType().getType());
		dto.setDescription(bus.getDescription());
		dto.setIdentityCode(bus.getIdentityCode());
		dto.setSeats(bus.getSeats());
		dto.setName(bus.getName());
		return dto;
	}
}
