package com.graduation.project.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.common.Utility;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Order;
import com.graduation.project.entity.Payment;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Status;
import com.graduation.project.entity.Ticket;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.repository.GiftCodeRepository;
import com.graduation.project.repository.OrderRepository;
import com.graduation.project.repository.PaymentRepository;
import com.graduation.project.repository.RankingRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.StatusRepository;
import com.graduation.project.repository.TicketRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.service.OrderService;
import com.graduation.project.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
	
	private PaymentRepository paymentRepository;
	private OrderRepository orderRepository;
	private SeatRepository seatRepository;
	private StatusRepository statusRepository;
	private UserRepository userRepository;
	private GiftCodeRepository giftCodeRepository;
	private UserService userService;
	private RankingRepository rankingRepository;
	private TicketRepository ticketRepository;

	public OrderServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
			SeatRepository seatRepository, StatusRepository statusRepository,
			TicketRepository ticketRepository, RankingRepository rankingRepository, UserRepository userRepository,
			UserService userService, GiftCodeRepository giftCodeRepository) {
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.seatRepository = seatRepository;
		this.statusRepository = statusRepository;
		this.userRepository = userRepository;
		this.giftCodeRepository = giftCodeRepository;
		this.userService = userService;
		this.rankingRepository = rankingRepository;
		this.ticketRepository = ticketRepository;
	}

	@Override
	public APIResponse BookingTicket(OrderRequest request) {
		APIResponse response = new APIResponse();
		try {
			Double paidAmount;
			Double giftPrice;
			Double deposit;
			Double eatingFee;
			Status status = null;
			Order order = new Order();
			Seat seat = seatRepository.findSeatByScheduleId(request.getScheduleId());
			Payment payment = paymentRepository.findById(request.getPaymentId()).orElse(null);
			order.setPayment(payment);
			
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
				status = statusRepository.findById(ConstraintMSG.STATUS_ORDERD).get();
			} else {
				paidAmount = 0.0;
				status = statusRepository.findById(ConstraintMSG.STATUS_PENDING).get();
			}
			if(request.getEatingFee() == null) {
				eatingFee = 0.0;
			}
			else {
				eatingFee = request.getEatingFee();
			}
			order.setStatus(status);
			double totalPrice = request.getSeatId().size() * seat.getPrice() + eatingFee - giftPrice;
			order.setTicketFee(request.getSeatId().size() * seat.getPrice() + eatingFee);
			int value = (int) totalPrice;
			order.setTotalPrice(totalPrice);
			if(totalPrice == paidAmount) {
				deposit = 0.0;
			}
			else {
				deposit = paidAmount;
			}
			order.setDeposit(deposit);
			
			User user = userRepository.findUserByNumberPhone(request.getCustomer().getPhoneNumber());
			if (user == null) {
				user = userService.createAnonymous(request.getCustomer());
			} else if (user != null && user.getAnonymous() == false) {
				Ranking ranking = null;
				Integer point = user.getPoint() + value / 10;
				user.setPoint(point);
				if (point > 100 && point < 500) {
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
				seat1.setStatus(order.getStatus());
				seatRepository.save(seat1);
				ticket.setSeat(seat1);
				ticket.setOrder(order);
				ticketRepository.save(ticket);
			}
			response.setData(order);
			response.setMessage(ConstraintMSG.BOOKING_SUCCESS_MSG);
			response.setSuccess(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse CancelBooking(Integer orderId) {
		APIResponse response = new APIResponse();
		Order order = orderRepository.findById(orderId).get();
		Status status = null;
		status = statusRepository.findById(ConstraintMSG.STATUS_CANCELED).get();
		order.setStatus(status);
		orderRepository.save(order);
		List<Ticket> tickets = order.getTickets();
		for(Ticket ticket:tickets) {
			Seat seat = seatRepository.findByTicket(ticket);
			ticketRepository.deleteTicketWhenCancel(seat.getId());
			status = statusRepository.findById(ConstraintMSG.STATUS_INITIALIZED).orElse(null);
			seat.setStatus(status);
			seatRepository.save(seat);
		}
		response.setData(order);
		response.setMessage(ConstraintMSG.CANCEL_ORDER_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public APIResponse ApprovalOrder(Integer orderId) {
		APIResponse response = new APIResponse();
		Order order = orderRepository.findById(orderId).get();
		Status status = statusRepository.findById(ConstraintMSG.STATUS_ORDERD).get();
		order.setStatus(status);
		orderRepository.save(order);
		List<Ticket> tickets = order.getTickets();
		for(Ticket ticket:tickets) {
			Seat seat = seatRepository.findByTicket(ticket);
			seat.setStatus(status);
			seatRepository.save(seat);
		}
		response.setData(order);
		response.setMessage(ConstraintMSG.APPROVAL_ORDER_MSG);
		response.setSuccess(true);
		return response;
	}
}
