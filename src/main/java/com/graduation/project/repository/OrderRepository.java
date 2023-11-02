package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Order;
import com.graduation.project.payload.response.DateAndTimeResponse;
import com.graduation.project.payload.response.DetailInFoCustomer;
import com.graduation.project.payload.response.DetailMoneyOrder;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(nativeQuery = true, value = "SELECT DISTINCT seat.price AS price, seat.eating_fee AS eatingFee, quantityTicket, orderId, quantityEating, (seat.eating_fee * quantityEating + price * quantityTicket - totalPrice) AS giftCode, deposit, totalPrice, (totalPrice - (seat.eating_fee * quantityEating + price * quantityTicket - totalPrice) - deposit) AS restPrice FROM seat, ticket JOIN (SELECT COUNT(*) AS quantityTicket, orders.id AS orderId, orders.quantity_eating AS quantityEating, orders.deposit AS deposit, orders.total_price AS totalPrice FROM orders, ticket WHERE orders.id =:orderId AND ticket.order_id = orders.id) orders ON orders.orderId = ticket.order_id WHERE seat.id = ticket.seat_id")
	DetailMoneyOrder findDetailMoney(Integer orderId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT orders.* FROM orders, seat, ticket, schedule WHERE seat.schedule_id =:scheduleId AND seat.schedule_id = schedule.id AND seat.id = ticket.seat_id AND orders.id = ticket.order_id")
	List<Order> findOrderByShcedule(Integer scheduleId);
	
	Order findByOrderCode(String orderCode);
	
	@Query(nativeQuery = true, value = "SELECT distinct  schedule.id as scheduleId FROM orders,shuttle,schedule,route,ticket,seat WHERE shuttle.id = schedule.shuttle_id and shuttle.route_id = route.id and orders.id = ticket.order_id and seat.id = ticket.seat_id and seat.schedule_id = schedule.id and orders.id =:orderId")
	Integer findScheduleIdByOrder(Integer orderId);
	
	@Query(nativeQuery = true, value = "SELECT concat(user.last_name,' ',user.first_name) as customerName, user.phone_number as phoneCustomer, user.email, orders.dropoff_point as dropOffPoint, orders.pickup_point as pickUpPoint FROM user, orders WHERE user.id = orders.user_id and orders.id =:orderId")
	DetailInFoCustomer findDetailInFoCustomerByOrderId(Integer orderId);
	
	@Query(nativeQuery = true, value = "SELECT distinct shuttle.start_time as time, schedule.date_start as date FROM orders, ticket, seat, schedule, shuttle where orders.id =:orderId and orders.id = ticket.order_id and seat.id  = ticket.seat_id and seat.schedule_id =  schedule.id and schedule.shuttle_id = shuttle.id")
	DateAndTimeResponse findDateAndTimeByOrderId(Integer orderId);

}
