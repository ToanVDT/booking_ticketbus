package com.graduation.project.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.common.Utility;
import com.graduation.project.dto.CurrentOrderDTO;
import com.graduation.project.dto.OrderDTO;
import com.graduation.project.dto.OrderDTOForCustomerSearch;
import com.graduation.project.dto.ReportScheduleDTO;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Order;
import com.graduation.project.entity.Payment;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Status;
import com.graduation.project.entity.Ticket;
import com.graduation.project.entity.User;
import com.graduation.project.payload.request.FilterOrderRequest;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.DateAndTimeResponse;
import com.graduation.project.payload.response.DetailInFoCustomer;
import com.graduation.project.payload.response.DetailMoneyOrder;
import com.graduation.project.repository.GiftCodeRepository;
import com.graduation.project.repository.OrderRepository;
import com.graduation.project.repository.PaymentRepository;
import com.graduation.project.repository.RankingRepository;
import com.graduation.project.repository.ScheduleRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.StatusRepository;
import com.graduation.project.repository.TicketRepository;
import com.graduation.project.repository.UserRepository;
import com.graduation.project.service.GiftCodeService;
import com.graduation.project.service.OrderService;
import com.graduation.project.service.UserService;
import com.graduation.project.thread.MailOrderStatusThread;
import com.graduation.project.thread.MailThanksLeterThread;
import com.graduation.project.thread.MailToBrandOwnerThread;
import com.graduation.project.thread.MailUpgradeRankThread;

@Service
public class OrderServiceImpl implements OrderService {

	private PaymentRepository paymentRepository;
	private OrderRepository orderRepository;
	private SeatRepository seatRepository;
	private StatusRepository statusRepository;
	private UserRepository userRepository;
	private GiftCodeRepository giftCodeRepository;
	private UserService userService;
	private RankingRepository rankingRepository;
	private TicketRepository ticketRepository;
	private ScheduleRepository scheduleRepository;
	private GiftCodeService giftCodeService;
	private ThreadPoolTaskExecutor taskExecutor;
	private MailOrderStatusThread mailOrderStatusThread;
	private MailToBrandOwnerThread mailToBrandOwnerThread;
	private MailUpgradeRankThread mailUpgradeRankThread;
	private MailThanksLeterThread mailThanksLeterThread;

	public OrderServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
			ThreadPoolTaskExecutor taskExecutor, SeatRepository seatRepository, StatusRepository statusRepository,
			MailOrderStatusThread mailOrderStatusThread, GiftCodeService giftCodeService,
			TicketRepository ticketRepository, RankingRepository rankingRepository, UserRepository userRepository,
			UserService userService, GiftCodeRepository giftCodeRepository,
			MailToBrandOwnerThread mailToBrandOwnerThread, MailUpgradeRankThread mailUpgradeRankThread,
			ScheduleRepository scheduleRepository, MailThanksLeterThread mailThanksLeterThread) {
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.seatRepository = seatRepository;
		this.statusRepository = statusRepository;
		this.userRepository = userRepository;
		this.giftCodeRepository = giftCodeRepository;
		this.userService = userService;
		this.rankingRepository = rankingRepository;
		this.ticketRepository = ticketRepository;
		this.scheduleRepository = scheduleRepository;
		this.giftCodeService = giftCodeService;
		this.mailUpgradeRankThread = mailUpgradeRankThread;
		this.taskExecutor = taskExecutor;
		this.mailOrderStatusThread = mailOrderStatusThread;
		this.mailToBrandOwnerThread = mailToBrandOwnerThread;
		this.mailThanksLeterThread = mailThanksLeterThread;
	}

	@Override
	public APIResponse BookingTicket(OrderRequest request) {
		APIResponse response = new APIResponse();
		try {
			Double paidAmount;
			String orderStatus;
			Double giftPrice;
			Double deposit;
			Double eatingFee;
			Integer quantityEating;
			String paymentStatus = "Chưa thanh toán";
			Status status = null;
			Order order = new Order();
			Seat seat = seatRepository.findSeatByScheduleId(request.getScheduleId());
			Payment payment = paymentRepository.findById(request.getPaymentId()).orElse(null);
			order.setPayment(payment);
			LocalDateTime now = LocalDateTime.now();
			LocalDate dateNow = now.toLocalDate();
			order.setDropoffPoint(request.getDropOff());
			order.setPickupPoint(request.getPickUp());
			order.setOrderCode(Utility.RandomOrderCode());
			order.setOrderDate(now);
			if (request.getQuantityEating() == null) {
				quantityEating = 0;
			} else {
				quantityEating = request.getQuantityEating();
			}
			order.setQuantityEating(quantityEating);
			if (request.getGiftCode() == null) {
				request.setGiftCode(ConstraintMSG.NO_GIFT_CODE);
			}
			GiftCode giftCode = giftCodeRepository.findByGiftCode(request.getGiftCode());
			if (giftCode != null && giftCode.getExpireDate().isAfter(dateNow) && !giftCode.getIsUsed()) {
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
			if (request.getQuantityEating() == null || request.getQuantityEating() == 0) {
				eatingFee = 0.0;
			} else {
				eatingFee = request.getQuantityEating() * seat.getEatingFee();
			}
			order.setStatus(status);
			double totalPrice = request.getSeatId().size() * seat.getPrice() + eatingFee - giftPrice;
			int value = (int) totalPrice;
			order.setTotalPrice(totalPrice);
			if (totalPrice == paidAmount) {
				order.setIsPaid(true);
				deposit = 0.0;
				paymentStatus = "Đã thanh toán";
			} else {
				order.setIsPaid(false);
				deposit = paidAmount;
				if (deposit > 0.0) {
					paymentStatus = "Đã cọc";
				}
			}
			order.setDeposit(deposit);

			User user = userRepository.findUserByNumberPhone(request.getCustomer().getPhoneNumber());
			if (user == null) {
				user = userService.createAnonymous(request.getCustomer());
			} else if (user != null && user.getAnonymous() == false) {
				Ranking ranking = null;
				Integer point = user.getPoint() + value / 10000;
				if (point >= 100 && point < 1000) {
					ranking = rankingRepository.findById(ConstraintMSG.RANK_MEMBER).get();
					if (user.getPoint() < 100) {
						GiftCode codeGenerated = (GiftCode) giftCodeService
								.saveGiftCode1Time(ranking.getId(), user.getId()).getData();
						SenderUpgradeRank(ranking, user, codeGenerated);
					}
				} else if (point >= 1000) {
					ranking = rankingRepository.findById(ConstraintMSG.RANK_VIPPER).get();
					if (user.getPoint() < 1000) {
						GiftCode codeGenerated = (GiftCode) giftCodeService
								.saveGiftCode1Time(ranking.getId(), user.getId()).getData();
						SenderUpgradeRank(ranking, user, codeGenerated);
					}
				} else {
					ranking = rankingRepository.findById(ConstraintMSG.RANK_NEW_MEMBER).get();

				}
				user.setPoint(point);
				user.setRank(ranking);
				userRepository.save(user);
			} else if (user != null && user.getAnonymous() == true) {
				user.setEmail(request.getCustomer().getEmail());
				user.setLastName(request.getCustomer().getLastName());
				user.setFirstName(request.getCustomer().getFirstName());
				user.setPhoneNumber(request.getCustomer().getPhoneNumber());
				userRepository.save(user);
			}
			order.setUser(user);
			orderRepository.save(order);
			for (Integer seatId : request.getSeatId()) {
				Ticket ticket = new Ticket();
				Seat seat1 = seatRepository.findById(seatId).get();
				seat1.setStatus(order.getStatus());
				seatRepository.save(seat1);
				ticket.setIsCanceled(false);
				ticket.setSeat(seat1);
				ticket.setOrder(order);
				ticketRepository.save(ticket);
			}
			Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
			Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
			User brandOwner = userRepository.findById(schedule.getShuttle().getRoute().getBrand().getUserId())
					.orElse(null);
			orderStatus = status.getStatus().equals("PENDING") ? "Chờ duyệt" : "Đã đặt";
			String routeName = schedule.getShuttle().getRoute().getStartPoint() + " - "
					+ schedule.getShuttle().getRoute().getEndPoint();
			String brandName = schedule.getShuttle().getRoute().getBrand().getBrandName();
			String datetimeTravel = schedule.getShuttle().getStartTime().toString() + ' '
					+ schedule.getDateStart().toString();
			String dropOffPoint = order.getDropoffPoint();
			Double totalPrices = order.getTotalPrice();
			String pickUpPoint = order.getPickupPoint();

			List<String> seatNames = new ArrayList<>();
			String seatName = null;
			List<Ticket> tickets = ticketRepository.findByOrderId(order.getId());
			for (Ticket ticket : tickets) {
				seatName = new String();
				seatName = ticket.getSeat().getName();
				seatNames.add(seatName);
			}
			String email = user.getEmail();
			String phone = user.getPhoneNumber();
			String fullName = user.getFirstName() + ' ' + user.getLastName();
			String lastName = user.getLastName();
			String orderCode = order.getOrderCode();
			SenderOrderStatusThread(dateNow, brandName, datetimeTravel, dropOffPoint, lastName, seatNames, totalPrices,
					paymentStatus, pickUpPoint, routeName, orderStatus, orderCode, user);
			SenderBrandOwnerThread(dateNow, brandName, email, fullName, phone, datetimeTravel, dropOffPoint, seatNames,
					totalPrices, paymentStatus, pickUpPoint, routeName, orderStatus, brandOwner);
			response.setData(order);
			response.setMessage(ConstraintMSG.BOOKING_SUCCESS_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse CancelBooking(Integer orderId) {
		APIResponse response = new APIResponse();
		try {
			Order order = orderRepository.findById(orderId).get();
			User user = order.getUser();
			Status status = null;
			status = statusRepository.findById(ConstraintMSG.STATUS_CANCELED).get();
			order.setStatus(status);
			String paymentStatus;
			orderRepository.save(order);
			if (order.getIsPaid() == true) {
				paymentStatus = "Đã thanh toán";
			} else {
				if (order.getDeposit() > 0) {
					paymentStatus = "Đã cọc";
				} else {
					paymentStatus = "Chưa thanh toán";
				}
			}
			List<String> seatNames = new ArrayList<>();
			String seatName = null;
			List<Ticket> tickets = order.getTickets();
			for (Ticket ticket : tickets) {
				Seat seat = seatRepository.findByTicket(ticket.getId());
				ticket.setIsCanceled(true);
				status = statusRepository.findById(ConstraintMSG.STATUS_INITIALIZED).orElse(null);
				seat.setStatus(status);
				seatRepository.save(seat);
				ticketRepository.save(ticket);
				seatName = new String();
				seatName = ticket.getSeat().getName();
				seatNames.add(seatName);
			}
			Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
			Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
			String orderStatus = "Đã hủy";
			String routeName = schedule.getShuttle().getRoute().getStartPoint() + " - "
					+ schedule.getShuttle().getRoute().getEndPoint();
			String brandName = schedule.getShuttle().getRoute().getBrand().getBrandName();
			String datetimeTravel = schedule.getShuttle().getStartTime().toString() + ' '
					+ schedule.getDateStart().toString();
			String dropOffPoint = order.getDropoffPoint();
			Double totalPrices = order.getTotalPrice();
			String pickUpPoint = order.getPickupPoint();
			LocalDate dateNow = order.getOrderDate().toLocalDate();
			String lastName = user.getLastName();
			String orderCode = order.getOrderCode();
			SenderOrderStatusThread(dateNow, brandName, datetimeTravel, dropOffPoint, lastName, seatNames, totalPrices,
					paymentStatus, pickUpPoint, routeName, orderStatus, orderCode, user);
			response.setData(order);
			response.setMessage(ConstraintMSG.CANCEL_ORDER_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse ApprovalOrder(Integer orderId) {
		APIResponse response = new APIResponse();
		try {
			Order order = orderRepository.findById(orderId).get();
			User user = userRepository.findByOrderId(orderId);
			Status status = null;
			status = statusRepository.findById(ConstraintMSG.STATUS_ORDERD).get();
			order.setStatus(status);
			String paymentStatus;
			orderRepository.save(order);
			if (order.getIsPaid() == true) {
				paymentStatus = "Đã thanh toán";
			} else {
				if (order.getDeposit() > 0) {
					paymentStatus = "Đã cọc";
				} else {
					paymentStatus = "Chưa thanh toán";
				}
			}
			List<String> seatNames = new ArrayList<>();
			String seatName = null;
			List<Ticket> tickets = order.getTickets();
			for (Ticket ticket : tickets) {
				Seat seat = seatRepository.findByTicket(ticket.getId());
				ticket.setIsCanceled(false);
				status = statusRepository.findById(ConstraintMSG.STATUS_ORDERD).orElse(null);
				seat.setStatus(status);
				seatRepository.save(seat);
				ticketRepository.save(ticket);
				seatName = new String();
				seatName = ticket.getSeat().getName();
				seatNames.add(seatName);
			}
			Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
			Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
			String orderStatus = "Đã đặt";
			String routeName = schedule.getShuttle().getRoute().getStartPoint() + " - "
					+ schedule.getShuttle().getRoute().getEndPoint();
			String brandName = schedule.getShuttle().getRoute().getBrand().getBrandName();
			String datetimeTravel = schedule.getShuttle().getStartTime().toString() + ' '
					+ schedule.getDateStart().toString();
			String dropOffPoint = order.getDropoffPoint();
			Double totalPrices = order.getTotalPrice();
			String pickUpPoint = order.getPickupPoint();
			LocalDate dateNow = order.getOrderDate().toLocalDate();
			String lastName = user.getLastName();
			String orderCode = order.getOrderCode();
			SenderOrderStatusThread(dateNow, brandName, datetimeTravel, dropOffPoint, lastName, seatNames, totalPrices,
					paymentStatus, pickUpPoint, routeName, orderStatus, orderCode, user);
			response.setData(order);
			response.setMessage(ConstraintMSG.APPROVAL_ORDER_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<OrderDTO> getOrderInSchedule(Integer scheduleId) {
		List<OrderDTO> dtos = new ArrayList<>();
		try {
			List<Order> orders = orderRepository.findOrderByShcedule(scheduleId);
			dtos = getOrder(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<OrderDTO> getOrderFilter(FilterOrderRequest request) {
		List<OrderDTO> dtos = new ArrayList<>();
		if (request.getIsPaid() == null && request.getStatus() != null) {
			dtos = getOrderWithOrderStatus(request.getScheduleId(), request.getStatus());
		} else if (request.getStatus() == null && request.getIsPaid() != null) {
			dtos = getOrderWithPaymentStatus(request.getScheduleId(), request.getIsPaid());
		} else if (request.getIsPaid() != null && request.getStatus() != null) {
			List<OrderDTO> listPayment = getOrderWithPaymentStatus(request.getScheduleId(), request.getIsPaid());
			List<OrderDTO> listOrder = getOrderWithOrderStatus(request.getScheduleId(), request.getStatus());
			Set<OrderDTO> setByPayment = new HashSet<OrderDTO>(listPayment);
			Set<OrderDTO> setByOrder = new HashSet<OrderDTO>(listOrder);
			Set<OrderDTO> result = new HashSet<>(setByPayment);
			result.retainAll(setByOrder);
			dtos.addAll(result);
		} else {
			dtos = getOrderInSchedule(request.getScheduleId());
		}
		return dtos;
	}

	public List<OrderDTO> getOrderWithPaymentStatus(Integer scheduleId, Integer isPaid) {
		List<OrderDTO> dtos = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		try {
			if (isPaid == 3) {
				orders = orderRepository.findOrderWithDeposit(scheduleId, 0);
			} else {
				orders = orderRepository.findOrderWithPaymentStatus(scheduleId, isPaid);
			}
			dtos = getOrder(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtos;
	}

	public List<OrderDTO> getOrderWithOrderStatus(Integer scheduleId, String status) {
		List<OrderDTO> dtos = new ArrayList<>();
		try {
			List<Order> orders = orderRepository.findOrderWithOrderStatus(scheduleId, status);
			dtos = getOrder(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtos;
	}

	public List<OrderDTO> getOrder(List<Order> orders) {
		List<OrderDTO> dtos = new ArrayList<>();
		OrderDTO dto = null;
		for (Order order : orders) {
			dto = new OrderDTO();
			dto.setId(order.getId());
			dto.setOrderDate(order.getOrderDate());
			dto.setOrderCode(order.getOrderCode());
			dto.setOrderStatus(order.getStatus().getStatus());
			dto.setDeposit(order.getDeposit());
			dto.setTotalPrice(order.getTotalPrice());
			if (!order.getIsPaid()) {
				if (order.getDeposit() == 0) {
					dto.setPaymentStatus(ConstraintMSG.NO_PAYMENT_STATUS);
				} else {
					dto.setPaymentStatus(ConstraintMSG.DEPOSIT_PAYMENT_STATUS);
				}
			} else {
				dto.setPaymentStatus(ConstraintMSG.PAYMENT_STATUS);
			}
			List<String> seatNames = new ArrayList<>();
			String seatName = null;

			List<Ticket> tickets = order.getTickets();
			for (Ticket ticket : tickets) {
				seatName = new String();
				seatName = ticket.getSeat().getName();
				seatNames.add(seatName);
			}
			dto.setListSeat(seatNames);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public APIResponse getOrderByOrderCode(String orderCode) {
		APIResponse response = new APIResponse();
		OrderDTOForCustomerSearch dto = new OrderDTOForCustomerSearch();
		Integer quantityTicket = 0;
		try {
			Order order = orderRepository.findByOrderCode(orderCode);
			if (order == null) {
				response.setMessage(ConstraintMSG.GET_DATA_MSG);
				response.setSuccess(false);
			} else {
				dto.setDeposit(order.getDeposit());
				dto.setOrderCode(orderCode);
				dto.setOrderId(order.getId());
				dto.setOrderDate(order.getOrderDate());
				dto.setOrderStatus(order.getStatus().getStatus());
				dto.setTotalPrice(order.getTotalPrice());
				if (!order.getIsPaid()) {
					if (order.getDeposit() == 0) {
						dto.setPaymentStatus(ConstraintMSG.NO_PAYMENT_STATUS);
					} else {
						dto.setPaymentStatus(ConstraintMSG.DEPOSIT_PAYMENT_STATUS);
					}
				} else {
					dto.setPaymentStatus(ConstraintMSG.PAYMENT_STATUS);
				}
				List<String> seatNames = new ArrayList<>();
				String seatName = null;
				List<Ticket> tickets = order.getTickets();
				for (Ticket ticket : tickets) {
					quantityTicket = quantityTicket + 1;
					seatName = new String();
					seatName = ticket.getSeat().getName();
					seatNames.add(seatName);
				}
				dto.setListSeat(seatNames);
				Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
				Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
				Seat seat = seatRepository.findSeatByScheduleId(scheduleId);
				dto.setEatingFee(seat.getEatingFee());
				dto.setPrice(seat.getPrice());
				dto.setQuantityEating(order.getQuantityEating());
				dto.setQuantityTicket(quantityTicket);
				dto.setGiftMoney(order.getQuantityEating() * seat.getEatingFee() + quantityTicket * seat.getPrice()
						- order.getTotalPrice());
				dto.setRestMoney(order.getTotalPrice() - order.getDeposit());
				dto.setBrandName(schedule.getShuttle().getRoute().getBrand().getBrandName());
				dto.setBrandPhone(schedule.getShuttle().getRoute().getBrand().getPhoneBrand());
				dto.setRouteName(schedule.getShuttle().getRoute().getStartPoint() + ' ' + '-' + ' '
						+ schedule.getShuttle().getRoute().getEndPoint());
				dto.setStartTime(schedule.getShuttle().getStartTime());
				dto.setTravelDate(schedule.getDateStart());
				response.setData(dto);
				response.setMessage(ConstraintMSG.GET_DATA_MSG);
				response.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse ConfirmPaid(Integer orderId) {
		APIResponse response = new APIResponse();
		try {
			Order order = orderRepository.findById(orderId).orElse(null);
			order.setIsPaid(true);
			orderRepository.save(order);
			response.setData(order);
			response.setMessage(ConstraintMSG.GET_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public DetailMoneyOrder getDetailPriceOrder(Integer orderId) {
		DetailMoneyOrder detailMoneyOrder = orderRepository.findDetailMoney(orderId);
		return detailMoneyOrder;
	}

	@Override
	public DetailInFoCustomer getInfoCusomerInOrder(Integer orderId) {
		DetailInFoCustomer detailInFoCustomer = orderRepository.findDetailInFoCustomerByOrderId(orderId);
		return detailInFoCustomer;
	}

	@Override
	public DateAndTimeResponse getDateAndTimeByOrderId(Integer orderId) {
		DateAndTimeResponse response = orderRepository.findDateAndTimeByOrderId(orderId);
		return response;
	}

	@Override
	public APIResponse EnterDeposit(Integer orderId, Double deposit) {
		APIResponse response = new APIResponse();
		try {
			Order order = orderRepository.findById(orderId).orElse(null);
			order.setDeposit(deposit);
			orderRepository.save(order);
			response.setData(order);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse getCurrentOrder(Integer userId) {
		APIResponse response = new APIResponse();
		List<CurrentOrderDTO> dtos = new ArrayList<>();
		try {
			List<Order> orders = orderRepository.findOrderByUserId(userId);
			CurrentOrderDTO dto = null;
			for (Order order : orders) {
				dto = new CurrentOrderDTO();
				dto.setId(order.getId());
				dto.setOrderDate(order.getOrderDate());
				dto.setOrderStatus(order.getStatus().getStatus());
				dto.setDeposit(order.getDeposit());
				dto.setTotalPrice(order.getTotalPrice());
				dto.setOrderCode(order.getOrderCode());
				Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
				Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
				dto.setBrandName(schedule.getShuttle().getRoute().getBrand().getBrandName());
				dto.setTravelDate(schedule.getDateStart());
				dto.setTravelTime(schedule.getShuttle().getStartTime());
				if (!order.getIsPaid()) {
					if (order.getDeposit() == 0) {
						dto.setPaymentStatus(ConstraintMSG.NO_PAYMENT_STATUS);
					} else {
						dto.setPaymentStatus(ConstraintMSG.DEPOSIT_PAYMENT_STATUS);
					}
				} else {
					dto.setPaymentStatus(ConstraintMSG.PAYMENT_STATUS);
				}
				List<String> seatNames = new ArrayList<>();
				String seatName = null;

				List<Ticket> tickets = order.getTickets();
				for (Ticket ticket : tickets) {
					seatName = new String();
					seatName = ticket.getSeat().getName();
					seatNames.add(seatName);
				}
				dto.setListSeat(seatNames);
				dtos.add(dto);
			}
			response.setData(dtos);
			response.setMessage(ConstraintMSG.GET_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse getPastOrder(Integer userId) {
		APIResponse response = new APIResponse();
		List<CurrentOrderDTO> dtos = new ArrayList<>();
		try {
			List<Order> orders = orderRepository.findPastOrderByUserId(userId);
			CurrentOrderDTO dto = null;
			for (Order order : orders) {
				dto = new CurrentOrderDTO();
				dto.setId(order.getId());
				dto.setOrderDate(order.getOrderDate());
				dto.setOrderStatus(order.getStatus().getStatus());
				dto.setDeposit(order.getDeposit());
				dto.setTotalPrice(order.getTotalPrice());
				dto.setOrderCode(order.getOrderCode());
				Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
				Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
				dto.setBrandName(schedule.getShuttle().getRoute().getBrand().getBrandName());
				dto.setTravelDate(schedule.getDateStart());
				dto.setTravelTime(schedule.getShuttle().getStartTime());
				if (!order.getIsPaid()) {
					if (order.getDeposit() == 0) {
						dto.setPaymentStatus(ConstraintMSG.NO_PAYMENT_STATUS);
					} else {
						dto.setPaymentStatus(ConstraintMSG.DEPOSIT_PAYMENT_STATUS);
					}
				} else {
					dto.setPaymentStatus(ConstraintMSG.PAYMENT_STATUS);
				}
				List<String> seatNames = new ArrayList<>();
				String seatName = null;

				List<Ticket> tickets = order.getTickets();
				for (Ticket ticket : tickets) {
					seatName = new String();
					seatName = ticket.getSeat().getName();
					seatNames.add(seatName);
				}
				dto.setListSeat(seatNames);
				dtos.add(dto);
			}
			response.setData(dtos);
			response.setMessage(ConstraintMSG.GET_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse getCanceledOrder(Integer userId) {
		APIResponse response = new APIResponse();
		List<CurrentOrderDTO> dtos = new ArrayList<>();
		try {
			List<Order> orders = orderRepository.findOrderCanceledByUserId(userId);
			CurrentOrderDTO dto = null;
			for (Order order : orders) {
				dto = new CurrentOrderDTO();
				dto.setId(order.getId());
				dto.setOrderDate(order.getOrderDate());
				dto.setOrderStatus(order.getStatus().getStatus());
				dto.setDeposit(order.getDeposit());
				dto.setTotalPrice(order.getTotalPrice());
				dto.setOrderCode(order.getOrderCode());
				Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
				Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
				dto.setBrandName(schedule.getShuttle().getRoute().getBrand().getBrandName());
				dto.setTravelDate(schedule.getDateStart());
				dto.setTravelTime(schedule.getShuttle().getStartTime());
				if (!order.getIsPaid()) {
					if (order.getDeposit() == 0) {
						dto.setPaymentStatus(ConstraintMSG.NO_PAYMENT_STATUS);
					} else {
						dto.setPaymentStatus(ConstraintMSG.DEPOSIT_PAYMENT_STATUS);
					}
				} else {
					dto.setPaymentStatus(ConstraintMSG.PAYMENT_STATUS);
				}
				List<String> seatNames = new ArrayList<>();
				String seatName = null;

				List<Ticket> tickets = order.getTickets();
				for (Ticket ticket : tickets) {
					seatName = new String();
					seatName = ticket.getSeat().getName();
					seatNames.add(seatName);
				}
				dto.setListSeat(seatNames);
				dtos.add(dto);
			}
			response.setData(dtos);
			response.setMessage(ConstraintMSG.GET_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Double totalMoneyOrderInSchedule(Integer scheduleId) {
		List<Order> orders = orderRepository.findOrderMoneyByShcedule(scheduleId);
		Double totalMoney = 0.0;
		for (Order order : orders) {
			if (order.getDeposit() == 0.0 || order.getDeposit() > 0.0 && order.getIsPaid()) {
				totalMoney = totalMoney + order.getTotalPrice();
			} else {
				totalMoney = totalMoney + order.getDeposit();
			}
		}
		return totalMoney;
	}

	@Override
	public List<Order> updateStatusOrder() {
		List<Order> orders = orderRepository.findAllOrderSchedule();
		Status status = statusRepository.findById(ConstraintMSG.STATUS_COMPLETED).orElse(null);
		String customerName = null;
		String brandName = null;
		User user = null;
		for (Order order : orders) {
			order.setStatus(status);
			orderRepository.save(order);
			Integer scheduleId = orderRepository.findScheduleIdByOrder(order.getId());
			Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
			user = userRepository.findUserByOrderId(order.getId());
			customerName = user.getLastName();
			brandName = schedule.getBus().getBrand().getBrandName();
			mailThanksLeterThread.setBrandName(brandName);
			mailThanksLeterThread.setCustomerName(customerName);
			mailThanksLeterThread.setUser(user);
			taskExecutor.execute(mailThanksLeterThread);
		}
		return orders;
	}

	public void SenderOrderStatusThread(LocalDate dateNow, String brandName, String datetimeTravel, String dropOffPoint,
			String customerName, List<String> seatNames, Double totalPrices, String paymentStatus, String pickUpPoint,
			String routeName, String orderStatus, String orderCode, User user) {
		mailOrderStatusThread.setBrandName(brandName);
		mailOrderStatusThread.setDateNow(dateNow);
		mailOrderStatusThread.setDatetimeTravel(datetimeTravel);
		mailOrderStatusThread.setDropOffPoint(dropOffPoint);
		mailOrderStatusThread.setCustomerName(customerName);
		mailOrderStatusThread.setSeatNames(seatNames);
		mailOrderStatusThread.setTotalPrices(totalPrices);
		mailOrderStatusThread.setPaymentStatus(paymentStatus);
		mailOrderStatusThread.setPickUpPoint(pickUpPoint);
		mailOrderStatusThread.setRouteName(routeName);
		mailOrderStatusThread.setOrderStatus(orderStatus);
		mailOrderStatusThread.setOrderCode(orderCode);
		mailOrderStatusThread.setUser(user);
		taskExecutor.execute(mailOrderStatusThread);
	}

	public void SenderBrandOwnerThread(LocalDate dateNow, String brandName, String email, String customerName,
			String phone, String datetimeTravel, String dropOffPoint, List<String> seatNames, Double totalPrices,
			String paymentStatus, String pickUpPoint, String routeName, String orderStatus, User user) {
		mailToBrandOwnerThread.setDateNow(dateNow);
		mailToBrandOwnerThread.setBrandName(brandName);
		mailToBrandOwnerThread.setEmail(email);
		mailToBrandOwnerThread.setFullName(customerName);
		mailToBrandOwnerThread.setPhone(phone);
		mailToBrandOwnerThread.setDatetimeTravel(datetimeTravel);
		mailOrderStatusThread.setDropOffPoint(dropOffPoint);
		mailToBrandOwnerThread.setSeatNames(seatNames);
		mailToBrandOwnerThread.setTotalPrices(totalPrices);
		mailToBrandOwnerThread.setPaymentStatus(paymentStatus);
		mailToBrandOwnerThread.setPickUpPoint(pickUpPoint);
		mailToBrandOwnerThread.setRouteName(routeName);
		mailToBrandOwnerThread.setOrderStatus(orderStatus);
		mailToBrandOwnerThread.setBrandOwner(user);
		taskExecutor.execute(mailToBrandOwnerThread);
	}

	public void SenderUpgradeRank(Ranking rank, User user, GiftCode codeGenerated) {
		mailUpgradeRankThread.setCodeGenerated(codeGenerated);
		mailUpgradeRankThread.setRanking(rank);
		mailUpgradeRankThread.setUser(user);
		taskExecutor.execute(mailUpgradeRankThread);
	}

	@Override
	public List<ReportScheduleDTO> getReportSchedule(Integer scheduleId) {
		List<Order> orders = orderRepository.findOrderMoneyByShcedule(scheduleId);
		List<ReportScheduleDTO> dtos = new ArrayList<ReportScheduleDTO>();
		ReportScheduleDTO dto = null;
		for (Order order : orders) {
			dto = new ReportScheduleDTO();
			dto.setCustomerName(order.getUser().getLastName() + " " + order.getUser().getFirstName());
			dto.setCustomerPhone(order.getUser().getPhoneNumber());
			dto.setOrderCode(order.getOrderCode());
			dto.setOrderDate(order.getOrderDate());
			if (order.getDeposit() == 0.0 || order.getDeposit() > 0.0 && order.getIsPaid()) {
				dto.setTotalPrice(order.getTotalPrice());
			} else {
				dto.setTotalPrice(order.getDeposit());
			}
			dtos.add(dto);
		}
		return dtos;
	}
}
