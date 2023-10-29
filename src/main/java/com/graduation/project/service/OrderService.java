package com.graduation.project.service;

import java.util.List;

import com.graduation.project.dto.OrderDTO;
import com.graduation.project.dto.OrderDTOForCustomerSearch;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;

public interface OrderService {

	public APIResponse BookingTicket(OrderRequest request);
	public APIResponse CancelBooking(Integer orderId);
	public APIResponse ApprovalOrder(Integer orderId);
	public List<OrderDTO> getOrderInSchedule(Integer scheduleId);
	public OrderDTOForCustomerSearch getOrderByOrderCode(String orderCode);
}
