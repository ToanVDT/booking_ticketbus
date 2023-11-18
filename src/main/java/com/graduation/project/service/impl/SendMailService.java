package com.graduation.project.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.entity.User;
import com.graduation.project.payload.request.MailOrderStatusRequest;
import com.graduation.project.payload.request.MailSendInformOrderToBrandOwnerRequest;
import com.graduation.project.service.EmailService;

@Service
public class SendMailService {

	@Autowired
	private EmailService emailService;

	public void sendMailForBrandOwner(LocalDate dateNow, String brandName, String email, String customerName,
			String phone, String datetimeTravel, String dropOffPoint, List<String> seatNames, Double totalPrices,
			String paymentStatus, String pickUpPoint, String routeName, String orderStatus, User user) {
		MailSendInformOrderToBrandOwnerRequest requestMail = new MailSendInformOrderToBrandOwnerRequest();
		requestMail.setBookingDate(dateNow);
		requestMail.setBrandName(brandName);
		requestMail.setCustomerEmail(email);
		requestMail.setCustomerName(customerName);
		requestMail.setCustomerPhone(phone);
		requestMail.setDateTimeTravel(datetimeTravel);
		requestMail.setDropOffPoint(dropOffPoint);
		requestMail.setListSeatOrderd(seatNames);
		requestMail.setTotalPrice(totalPrices);
		requestMail.setPaymentStatus(paymentStatus);
		requestMail.setPickUpPoint(pickUpPoint);
		requestMail.setRouteName(routeName);
		requestMail.setOrderStatus(orderStatus);
		emailService.sendMailInformOrderToBrandOwner(requestMail, user);
	}

	public void sendMailOrderStatus(LocalDate dateNow, String brandName, String datetimeTravel, String dropOffPoint,String customerName,
			List<String> seatNames, Double totalPrices, String paymentStatus, String pickUpPoint, String routeName,
			String orderStatus,String orderCode, User user) {
		MailOrderStatusRequest requestMail = new MailOrderStatusRequest();
		requestMail.setBookingDate(dateNow);
		requestMail.setBrandName(brandName);
		requestMail.setCustomerName(customerName);
		requestMail.setDateTimeTravel(datetimeTravel);
		requestMail.setDropOffPoint(dropOffPoint);
		requestMail.setListSeatOrderd(seatNames);
		requestMail.setTotalPrice(totalPrices);
		requestMail.setPaymentStatus(paymentStatus);
		requestMail.setPickUpPoint(pickUpPoint);
		requestMail.setRouteName(routeName);
		requestMail.setOrderStatus(orderStatus);
		requestMail.setOrderCode(orderCode);
		emailService.sendMailOrderStatus(requestMail, user);
	}
	public void sendMailThanksLeter(String customerName,String brandName,User user) {
		emailService.sendMailThanksLeter(customerName, brandName, user);
	}
}
