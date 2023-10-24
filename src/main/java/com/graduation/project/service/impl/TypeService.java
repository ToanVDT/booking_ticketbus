package com.graduation.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.TypeDTO;
import com.graduation.project.entity.BusType;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.repository.TypeRepository;

@Service
public class TypeService {

	@Autowired
	private TypeRepository typeRepository;
	
	public APIResponse getAllType() {
		APIResponse response = new APIResponse();
		List<BusType> types = typeRepository.findAll();
		List<TypeDTO> list = new ArrayList<>();
		TypeDTO dto = null;
		for(BusType type:types) {
			dto = new TypeDTO();
			dto.setId(type.getId());
			dto.setType(type.getType());
			list.add(dto);
		}
		response.setData(list);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}
}
