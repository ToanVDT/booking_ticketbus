package com.graduation.project.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.GiftCodeDTO;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.repository.GiftCodeRepository;
import com.graduation.project.repository.RankingRepository;
import com.graduation.project.service.GiftCodeService;

@Service
public class GiftCodeServiceImpl implements GiftCodeService{
	@Autowired
	private GiftCodeRepository giftCodeRepository;

	@Autowired
	private RankingRepository rankingRepository;

	@Override
	public APIResponse saveGiftCode(Integer rankId, Integer userId) {
		APIResponse response = new APIResponse();
		GiftCode giftCode = new GiftCode();
		LocalDate now = LocalDate.now();
		giftCode.setExpireDate(now.plusDays(10));
		Ranking ranking = rankingRepository.findById(rankId).orElse(null);
		giftCode.setRank(ranking);
		giftCode.setGiftCode(generateGiftCode(rankId));
		giftCode.setUserId(userId);
		giftCode.setIsUsed(false);
		giftCodeRepository.save(giftCode);
		response.setData(giftCode);
		response.setMessage(ConstraintMSG.CREATE_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	public String generateGiftCode(Integer rankId) {
		Ranking ranking = rankingRepository.findById(rankId).orElse(null);
		String PREFIX = ranking.getRankName();
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder s = new StringBuilder(9);
		int y;
		for (y = 0; y < 9; y++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			s.append(AlphaNumericString.charAt(index));
		}
		return PREFIX + s.toString();
	}
	@Override
	public List<GiftCodeDTO> getGiftCodeInUser(Integer userId) {
		List<GiftCode> giftCodes = giftCodeRepository.findByUserId(userId);
		GiftCodeDTO dto = null;
		List<GiftCodeDTO> dtos = new ArrayList<>();
		for(GiftCode giftCode:giftCodes) {
			dto = new GiftCodeDTO();
			Ranking ranking = giftCode.getRank();
			dto.setPromotion(ranking.getMoneyReduced());
			dto.setExpireDate(giftCode.getExpireDate());
			dto.setGiftCode(giftCode.getGiftCode());
			dto.setIsUsed(giftCode.getIsUsed());
			dtos.add(dto);
		}
		return dtos;
	}
}
