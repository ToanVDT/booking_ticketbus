package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduation.project.entity.GiftCode;

public interface GiftCodeRepository extends JpaRepository<GiftCode, Integer>{

	GiftCode findByGiftCode(String giftCode);

	List<GiftCode> findByUserId(Integer userId);
}
