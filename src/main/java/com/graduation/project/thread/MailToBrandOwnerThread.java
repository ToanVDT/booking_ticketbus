package com.graduation.project.thread;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graduation.project.entity.User;
import com.graduation.project.service.impl.SendMailService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
@AllArgsConstructor
//@NoArgsConstructor
public class MailToBrandOwnerThread implements Runnable{

	@Autowired
	private SendMailService sendMailService;
	
	private LocalDateTime dateNow;
	private String brandName;
	private String datetimeTravel;
	private String dropOffPoint;
	private String fullName;
	private String email;
	private String phone;
	private List<String> seatNames; 
	private Double totalPrices;
	private String paymentStatus; 
	private String pickUpPoint;
	private String routeName;
	private String orderStatus; 
	private User brandOwner;
	
	public MailToBrandOwnerThread() {
		
	}
	@Override
	public synchronized void run() {
		sendMailService.sendMailForBrandOwner(dateNow, brandName, email, fullName, phone, datetimeTravel, dropOffPoint, seatNames,
				totalPrices, paymentStatus, pickUpPoint, routeName, orderStatus, brandOwner);
	}
}
