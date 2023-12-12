package com.graduation.project.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.User;
import com.graduation.project.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Data
@AllArgsConstructor
public class MailWelcomeNewJoinerThread implements Runnable {

	@Autowired
	private EmailService emailService;

	private Ranking ranking;
	private GiftCode codeGenerated;
	private User user;

	public MailWelcomeNewJoinerThread() {
	}

	@Override
	public synchronized void run() {
		emailService.sendMailWelcomNewJoiner(ranking,user,codeGenerated	);
	}

}
