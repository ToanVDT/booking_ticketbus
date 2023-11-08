package com.graduation.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.dto.OrderDTO;
import com.graduation.project.payload.request.OrderRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.DateAndTimeResponse;
import com.graduation.project.payload.response.DetailInFoCustomer;
import com.graduation.project.payload.response.DetailMoneyOrder;
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
	@PostMapping("/order/confirmPaid")
	private ResponseEntity<APIResponse> ConfirmPaid(@RequestParam Integer orderId){
		final APIResponse response = orderService.ConfirmPaid(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/orders")
	private ResponseEntity<APIResponse> getOrdersInSchedule(@RequestParam Integer scheduleId){
		final List<OrderDTO> response = orderService.getOrderInSchedule(scheduleId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	@GetMapping("/order")
	private ResponseEntity<APIResponse> getOrdersByOrderCode(@RequestParam String orderCode){
		final APIResponse response = orderService.getOrderByOrderCode(orderCode);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/order/detailmoney")
	private ResponseEntity<APIResponse> getDetailMoneyInOrder(@RequestParam Integer orderId){
		final DetailMoneyOrder response = orderService.getDetailPriceOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	@GetMapping("/order/detailcustomer")
	private ResponseEntity<APIResponse> getDetailInFoCustomer(@RequestParam Integer orderId){
		final DetailInFoCustomer response = orderService.getInfoCusomerInOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	@GetMapping("/order/datetime")
	private ResponseEntity<APIResponse> getDateAndTimeInScheduleByOrderId(@RequestParam Integer orderId){
		final DateAndTimeResponse response = orderService.getDateAndTimeByOrderId(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(ConstraintMSG.GET_DATA_MSG,response,true));
	}
	@GetMapping("/order/current")
	private ResponseEntity<APIResponse> getCurrentOrderForCustomer(@RequestParam Integer userId){
		final APIResponse response = orderService.getCurrentOrder(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/order/canceled")
	private ResponseEntity<APIResponse> getCanceledOrderForCustomer(@RequestParam Integer userId){
		final APIResponse response = orderService.getCanceledOrder(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping("/order/past")
	private ResponseEntity<APIResponse> getPastOrderForCustomer(@RequestParam Integer userId){
		final APIResponse response = orderService.getPastOrder(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping("/order/deposit")
	private ResponseEntity<APIResponse> enterDeposit(@RequestParam Integer orderId, @RequestParam Double deposit){
		final APIResponse response = orderService.EnterDeposit(orderId, deposit);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
