package com.graduation.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.graduation.project.entity.Order;
import com.graduation.project.payload.response.DetailMoneyOrder;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(nativeQuery = true, value = "SELECT distinct seat.price AS price, seat.eating_fee AS eatingFee, quantityTicket, orderId, ticketFee, (ticketFee-price*quantityTicket)/seat.eating_fee as quantityEating, giftCode, deposit, totalPrice FROM seat, ticket JOIN (SELECT COUNT(*) AS quantityTicket, orders.id AS orderId, orders.ticket_fee as ticketFee, (orders.total_price - orders.ticket_fee) as giftCode, orders.deposit as deposit, orders.total_price as totalPrice FROM orders, ticket WHERE orders.id =:orderId AND ticket.order_id = orders.id) orders ON orders.orderId = ticket.order_id WHERE seat.id = ticket.seat_id")
	DetailMoneyOrder findDetailMoney(Integer orderId);

}
