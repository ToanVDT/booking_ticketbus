package com.graduation.project.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
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
	public APIResponse saveGiftCode(Integer rankId) {
		APIResponse response = new APIResponse();
		GiftCode giftCode = new GiftCode();
		LocalDateTime now = LocalDateTime.now();
		giftCode.setExpireDate(now.plusDays(10));
		Ranking ranking = rankingRepository.findById(rankId).orElse(null);
		giftCode.setRank(ranking);
		giftCode.setGiftCode(generateGiftCode(rankId));
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
}
