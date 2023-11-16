package com.graduation.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduation.project.entity.Shuttle;
import com.graduation.project.payload.response.SearchShuttleResponse;
import com.graduation.project.payload.response.ShuttleResponse;

public interface ShuttleRepository extends JpaRepository<Shuttle, Integer>{

	@Query(nativeQuery = true, value = "SELECT DISTINCT shuttle.id as id, shuttle.start_time as startTime, shuttle.end_time as endTime, CONCAT( 'Từ ', route.start_point, ' Đến ', route.end_point ) as routeName FROM shuttle, route, brand WHERE shuttle.route_id = route.id AND route.brand_id = brand.id AND route.brand_id =:brandId")
	List<ShuttleResponse> findShuttle(Integer brandId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM shuttle WHERE shuttle.route_id =:routeId")
	List<Shuttle> findShuttleByRoute(Integer routeId);
	
	@Query(nativeQuery = true, value = "SELECT seat3.price AS price, seat3.eating_fee AS eatingFee, seat3.emptySeats, xe.* FROM (SELECT DISTINCT seat.eating_fee, seat.schedule_id, seat.price, seat2.emptys AS emptySeats FROM seat JOIN (SELECT COUNT(id) AS emptys, seat.schedule_id FROM seat WHERE seat.status_id = 5 GROUP BY seat.schedule_id) AS seat2 ON seat.schedule_id = seat2.schedule_id) AS seat3 JOIN (SELECT bus.seats AS seats, brand.image AS image, brand.brand_name AS brandName, brand.phone_brand AS brandPhone, bus_type.type AS type, shuttles.* FROM bus, brand, bus_type, (SELECT shuttle.id AS shuttleId, schedule.bus_id AS busId, schedule.id AS scheduleId, shuttle.start_time AS startTime, shuttle.end_time AS endTime, route.start_point AS startPoint, route.end_point AS endPoint, route.id AS routeId FROM shuttle, schedule, route, brand WHERE shuttle.route_id = route.id AND schedule.shuttle_id = shuttle.id AND brand.id = route.brand_id AND route.start_point =:startPoint AND route.end_point =:endPoint AND schedule.date_start =:travelDate AND brand.user_id NOT IN (SELECT user.id FROM user WHERE user.active = FALSE) AND schedule.id NOT IN (SELECT schedule.id FROM schedule, shuttle WHERE schedule.shuttle_id = shuttle.id AND schedule.date_start = DATE(NOW()) AND ADDTIME(TIME(NOW()), '01:00:00') > shuttle.start_time)) shuttles WHERE bus.id = shuttles.busId AND bus.type_id = bus_type.id AND brand.id = bus.brand_id) xe ON seat3.schedule_id = xe.scheduleId")
	List<SearchShuttleResponse> findShuttleAvailable(@Param("startPoint") String startPoint, @Param("endPoint") String endPoint, @Param("travelDate") LocalDate travelDate);
}
