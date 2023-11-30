package com.graduation.project.service;

import java.util.List;

import com.graduation.project.dto.OrderDTO;
import com.graduation.project.dto.ReportScheduleDTO;
import com.graduation.project.entity.Order;
import com.graduation.project.payload.request.FilterOrderRequest;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.DateAndTimeResponse;
import com.graduation.project.payload.response.DetailInFoCustomer;
import com.graduation.project.payload.response.DetailMoneyOrder;

public interface OrderService {

	APIResponse BookingTicket(OrderRequest request);

	APIResponse CancelBooking(Integer orderId);

	APIResponse ApprovalOrder(Integer orderId);

	List<OrderDTO> getOrderInSchedule(Integer scheduleId);
	
	List<OrderDTO> getOrderFilter(FilterOrderRequest request);

	APIResponse getCurrentOrder(Integer userId);

	APIResponse getOrderByOrderCode(String orderCode);
	
	List<ReportScheduleDTO> getReportSchedule(Integer scheduleId);

	APIResponse ConfirmPaid(Integer orderId);

	DetailMoneyOrder getDetailPriceOrder(Integer orderId);

	DetailInFoCustomer getInfoCusomerInOrder(Integer orderId);

	DateAndTimeResponse getDateAndTimeByOrderId(Integer orderId);

	APIResponse EnterDeposit(Integer orderId, Double deposit);

	APIResponse getCanceledOrder(Integer userId);

	APIResponse getPastOrder(Integer userId);

	List<Order> updateStatusOrder();
	
	Double totalMoneyOrderInSchedule(Integer scheduleId);
}
