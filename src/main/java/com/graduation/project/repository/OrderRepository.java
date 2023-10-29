package com.graduation.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Order;
import com.graduation.project.payload.response.DetailMoneyOrder;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(nativeQuery = true, value = "SELECT distinct seat.price AS price, seat.eating_fee AS eatingFee, quantityTicket, orderId, quantityEating, (quantityTicket*price + quantityEating*seat.eating_fee - totalPrice)as  giftCode, deposit, totalPrice FROM seat, ticket JOIN ( SELECT COUNT(*) AS quantityTicket, orders.id AS orderId, orders.deposit as deposit, orders.total_price as totalPrice, orders.quantity_eating as quantityEating FROM orders, ticket WHERE orders.id =:orderId AND ticket.order_id = orders.id ) orders ON orders.orderId = ticket.order_id WHERE seat.id = ticket.seat_id")
	DetailMoneyOrder findDetailMoney(Integer orderId);
	
	@Query(nativeQuery = true, value = "SELECT distinct orders.* FROM orders, seat, ticket,schedule WHERE seat.schedule_id =:scheduleId and seat.schedule_id = schedule.id and seat.id = ticket.seat_id and ticket.order_id = orders.id")
	List<Order> findOrderByShcedule(Integer scheduleId);
	
	Order findByOrderCode(String orderCode);
	
	@Query(nativeQuery = true, value = "SELECT distinct  schedule.id as scheduleId FROM orders,shuttle,schedule,route,ticket,seat WHERE shuttle.id = schedule.shuttle_id and shuttle.route_id = route.id and orders.id = ticket.order_id and seat.id = ticket.seat_id and seat.schedule_id = schedule.id and orders.id =:orderId")
	Integer findScheduleIdByOrder(Integer orderId);

}
