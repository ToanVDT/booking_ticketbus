package com.graduation.project.config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.graduation.project.payload.EmailDetails;
import com.graduation.project.service.EmailService;
import com.graduation.project.service.OrderService;

@Component
public class Schedule {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired 
	private EmailService emailService;

	@Scheduled(cron = "0 32 1 * * *")
	public void scheduleTaskUsingCronExpression() {

		 try {
			long now = System.currentTimeMillis() / 1000;
			    System.out.println(
			      "schedule tasks using cron jobs - " + now);
			orderService.updateStatusOrder();
//			EmailDetails email = new EmailDetails();
//			email.setFrom("fromemail@gmail.com");
//			email.setTemplate("notification.html");
////        email.setProperties(properties);
//			
//			String regex = "^(.+)@(.+)$";
//			Pattern pattern = Pattern.compile(regex);
//      
//			Matcher matcher = pattern.matcher("vanthanhtoan771@gmail.com");
//			if (matcher.matches()) {
//				email.setTo("vanthanhtoan771@gmail.com");
//				emailService.sendSimpleMail(email);
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}

	public Schedule() {
	System.out.println("gdsghj");
	}
}
