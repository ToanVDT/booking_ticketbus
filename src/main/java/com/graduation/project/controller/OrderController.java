package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.OrderDTO;
import com.graduation.project.dto.OrderDTOForCustomerSearch;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.service.OrderService;

@RestController
@RequestMapping
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	private ResponseEntity<APIResponse> Booking(@RequestBody OrderRequest request){
		final APIResponse response = orderService.BookingTicket(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping("/order/approval")
	private ResponseEntity<APIResponse> ApprovalOrder(@RequestParam Integer orderId){
		final APIResponse response = orderService.ApprovalOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping("/order/cancel")
	private ResponseEntity<APIResponse> CancelOrder(@RequestParam Integer orderId){
		final APIResponse response = orderService.CancelBooking(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/orders")
	private ResponseEntity<APIResponse> getOrdersInSchedule(@RequestParam Integer scheduleId){
		final List<OrderDTO> response = orderService.getOrderInSchedule(scheduleId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	@GetMapping("/order")
	private ResponseEntity<APIResponse> getOrdersByOrderCode(@RequestParam String orderCode){
		final OrderDTOForCustomerSearch response = orderService.getOrderByOrderCode(orderCode);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
}
