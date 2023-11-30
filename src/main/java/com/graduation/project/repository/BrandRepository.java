package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Brand;
import com.graduation.project.payload.response.BrandResponse;

public interface BrandRepository extends JpaRepository<Brand, Integer>{

	@Query(nativeQuery = true, value = "SELECT concat(u.last_name,' ',u.first_name) as name, u.username as username, u.email as email, u.phone_number as personalPhone, b.brand_name as brandName, b.address as addressBrand, b.phone_brand as phoneBrand, u.active as active FROM brand b, user u WHERE u.id = b.user_id")
	List<BrandResponse> findAllBrand();
	
	Brand findByUserId(Integer userId);
	Brand findByBrandName(String brandName);
	@Query(nativeQuery = true, value="SELECT * FROM brand where brand.phone_brand=:phoneNumber")
	Brand findByPhoneBrand(String phoneNumber);
}
