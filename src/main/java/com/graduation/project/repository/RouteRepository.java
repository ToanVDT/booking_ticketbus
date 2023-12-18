package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Route;
import com.graduation.project.payload.response.RouteResponse;
import com.graduation.project.payload.response.RouteResponseForDropDown;

public interface RouteRepository extends JpaRepository<Route, Integer>{

	@Query(nativeQuery = true, value = "SELECT id as id, start_point as startPoint, end_point as endPoint,duration FROM route WHERE route.brand_id=:brandId")
	List<RouteResponse> findAllRoute(Integer brandId);
	
	@Query(nativeQuery = true, value = "SELECT id as id,CONCAT(start_point,'-',end_point) as routeName FROM route WHERE route.brand_id=:brandId")
	List<RouteResponseForDropDown> findRouteCustomName(Integer brandId);
	
	@Query(nativeQuery = true,value = "select * from route where route.start_point=:startPoint and route.end_point=:endPoint and route.brand_id=:brandId")
	Route findExistsRoute(String startPoint, String endPoint, Integer brandId);
}
