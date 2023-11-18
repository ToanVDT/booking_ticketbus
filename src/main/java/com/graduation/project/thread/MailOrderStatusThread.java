package com.graduation.project.thread;

import java.time.LocalDate;
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
public class MailOrderStatusThread implements Runnable{

	@Autowired
	private SendMailService sendMailService;
	
	private LocalDate dateNow;
	private String brandName;
	private String datetimeTravel;
	private String dropOffPoint;
	private String customerName;
	private List<String> seatNames; 
	private Double totalPrices;
	private String paymentStatus; 
	private String pickUpPoint;
	private String routeName;
	private String orderCode;
	private String orderStatus; 
	private User user;
	
	public MailOrderStatusThread() {
		System.out.println("init thread");
	}
	@Override
	public synchronized void run() {
		sendMailService.sendMailOrderStatus(dateNow, brandName, datetimeTravel, dropOffPoint,customerName, seatNames, totalPrices, paymentStatus,
				pickUpPoint, routeName, orderStatus,orderCode, user);
	}
}
