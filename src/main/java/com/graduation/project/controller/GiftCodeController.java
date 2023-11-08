package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.GiftCodeDTO;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.GiftCodeService;

@RestController
@RequestMapping("/giftcode")
public class GiftCodeController {

	@Autowired
	private GiftCodeService giftCodeService;

	@PostMapping()
	private ResponseEntity<APIResponse> saveGiftCode(@RequestParam Integer rankId, @RequestParam Integer userId) {
		final APIResponse response = giftCodeService.saveGiftCode(rankId, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping()
	private ResponseEntity<APIResponse> getGiftCodeOffUser(@RequestParam Integer userId) {
		final List<GiftCodeDTO> response = giftCodeService.getGiftCodeInUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG, response, true));
	}
}
