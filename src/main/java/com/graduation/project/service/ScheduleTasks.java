package com.graduation.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class ScheduleTasks {
	@Autowired
	private OrderService orderService;

	@Scheduled(cron = "0 57 23 * * *")
	public void scheduleTaskUsingCronExpression() {

		try {
			long now = System.currentTimeMillis() / 1000;
			System.out.println("schedule tasks using cron jobs - " + now);
			orderService.updateStatusOrder();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
