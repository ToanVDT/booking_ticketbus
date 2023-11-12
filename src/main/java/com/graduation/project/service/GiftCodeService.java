package com.graduation.project.service;

import java.util.List;

import com.graduation.project.dto.GiftCodeDTO;
import com.graduation.project.payload.response.APIResponse;

public interface GiftCodeService {

	public APIResponse saveGiftCode(Integer rankId, Integer userId);

	List<GiftCodeDTO> getGiftCodeInUser(Integer userId);
	
	APIResponse checkGiftCodeValid(String giftCode);
}
