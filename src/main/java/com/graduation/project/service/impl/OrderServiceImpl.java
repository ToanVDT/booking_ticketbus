package com.graduation.project.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.common.Utility;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Order;
import com.graduation.project.entity.OrderStatus;
import com.graduation.project.entity.Payment;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Ticket;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.repository.GiftCodeRepository;
import com.graduation.project.repository.OrderRepository;
import com.graduation.project.repository.OrderStatusRepository;
import com.graduation.project.repository.PaymentRepository;
import com.graduation.project.repository.RankingRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.TicketRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.service.OrderService;
import com.graduation.project.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
	
	private PaymentRepository paymentRepository;
	private OrderRepository orderRepository;
	private SeatRepository seatRepository;
	private OrderStatusRepository orderStatusRepository;
	private UserRepository userRepository;
	private GiftCodeRepository giftCodeRepository;
	private UserService userService;
	private RankingRepository rankingRepository;
	private TicketRepository ticketRepository;

	public OrderServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
			SeatRepository seatRepository, OrderStatusRepository orderStatusRepository,
			TicketRepository ticketRepository, RankingRepository rankingRepository, UserRepository userRepository,
			UserService userService, GiftCodeRepository giftCodeRepository) {
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.seatRepository = seatRepository;
		this.orderStatusRepository = orderStatusRepository;
		this.userRepository = userRepository;
		this.giftCodeRepository = giftCodeRepository;
		this.userService = userService;
		this.rankingRepository = rankingRepository;
		this.ticketRepository = ticketRepository;
	}

	@Override
	public APIResponse BookingTicket(OrderRequest request) {
		APIResponse response = new APIResponse();
		Double paidAmount;
		Double giftPrice;
		Order order = new Order();
		Seat seat = seatRepository.findSeatByScheduleId(request.getScheduleId());
		Payment payment = paymentRepository.findById(request.getPaymentId()).orElse(null);
		order.setPayment(payment);
		OrderStatus orderStatus = orderStatusRepository.findById(1).orElse(null);
		order.setOrderStatus(orderStatus);

		LocalDateTime now = LocalDateTime.now();
		order.setDropoffPoint(request.getDropOff());
		order.setPickupPoint(request.getPickUp());
		order.setOrderCode(Utility.RandomOrderCode());
		order.setOrderDate(now);
		if (request.getGiftCode() == null) {
			request.setGiftCode(ConstraintMSG.NO_GIFT_CODE);
		}
		GiftCode giftCode = giftCodeRepository.findByGiftCode(request.getGiftCode());
		if (giftCode != null && giftCode.getExpireDate().isAfter(now) && !giftCode.getIsUsed()) {
			giftPrice = giftCode.getRank().getMoneyReduced();
			giftCode.setIsUsed(true);
		} else {
			giftPrice = 0.0;
		}

		if (payment.getPaymentType().equals(ConstraintMSG.METHOD_PAYMENT_CREDIT)) {
			paidAmount = request.getPaidAmount();
		} else {
			paidAmount = 0.0;
		}
		double totalPrice = request.getSeatId().size() * seat.getPrice() - paidAmount - giftPrice;
		int value = (int) totalPrice;
		order.setTotalPrice(totalPrice);
		User user = userRepository.findUserByNumberPhone(request.getCustomer().getPhoneNumber());
		if (user == null) {
			user = userService.createAnonymous(request.getCustomer());
		} else if (user != null && user.getAnonymous() == false) {
			Ranking ranking = null;
			Integer point = user.getPoint() + value / 10;
			user.setPoint(point);
			if (point > 100) {
				ranking = rankingRepository.findById(ConstraintMSG.RANK_MEMBER).get();
			} else if (point > 500) {
				ranking = rankingRepository.findById(ConstraintMSG.RANK_VIPPER).get();
			} else {
				ranking = rankingRepository.findById(ConstraintMSG.RANK_NEW_MEMBER).get();
			}
			user.setRank(ranking);
			userRepository.save(user);
		}
		order.setUser(user);
		orderRepository.save(order);
		for (Integer seatId : request.getSeatId()) {
			Ticket ticket = new Ticket();
			Seat seat1 = seatRepository.findById(seatId).get();
			seat1.setBooked(true);
			seatRepository.save(seat1);
			ticket.setSeat(seat1);
			ticket.setDeposit(0.0);
			ticket.setOrder(order);
			ticketRepository.save(ticket);
		}
		response.setData(order);
		response.setMessage(ConstraintMSG.BOOKING_SUCCESS_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse CancelBooking() {
		// TODO Auto-generated method stub
		return null;
	}


}
