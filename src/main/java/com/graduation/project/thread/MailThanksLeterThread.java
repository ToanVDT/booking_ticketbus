package com.graduation.project.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graduation.project.entity.User;
import com.graduation.project.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
@AllArgsConstructor
//@NoArgsConstructor
public class MailThanksLeterThread implements Runnable{

	@Autowired
	private EmailService emailService;
	
	private String customerName;
	private String brandName;
	private User user;
	
	public MailThanksLeterThread() {
		System.out.println("init thread");
	}
	@Override
	public synchronized void run() {
		emailService.sendMailThanksLeter(customerName, brandName, user);
	}
}
