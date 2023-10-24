package com.graduation.project.repository;

import java.util.Date;
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
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) as emptySeats, seat.price as price, xe.* FROM seat join( SELECT bus.seats as seats, brand.image as image, brand.brand_name as brandName, bus_type.type as type, shuttles.* FROM bus, brand, bus_type, ( SELECT shuttle.id as shuttleId, shuttle.bus_id as busId, shuttle.start_time as startTime, shuttle.end_time as endTime, route.* FROM shuttle join( SELECT route.start_point as startPoint, route.end_point as endPoint, route.id as routeId FROM route, brand WHERE brand.id = route.brand_id and route.start_point =:startPoint and route.end_point =:endPoint ) route on route.routeId = shuttle.route_id WHERE day(shuttle.start_time) = day(:startTime)and month(shuttle.start_time) = month(:startTime) and year(shuttle.start_time) = year(:startTime) ) shuttles WHERE bus.id = shuttles.busId AND bus.type_id = bus_type.id and brand.id = bus.brand_id ) xe on seat.shuttle_id = xe.shuttleId WHERE seat.booked = false GROUP BY shuttle_id, price")
	List<SearchShuttleResponse> findShuttleAvailable(@Param("startPoint") String startPoint, @Param("endPoint") String endPoint, @Param("startTime") Date startTime);
}
