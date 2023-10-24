package com.graduation.project.service;

import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;

public interface OrderService {

	public APIResponse BookingTicket(OrderRequest request);
	public APIResponse CancelBooking(Integer orderId);
	public APIResponse ApprovalOrder(Integer orderId);
}
