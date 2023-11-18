package com.graduation.project.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.User;
import com.graduation.project.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
@AllArgsConstructor
//@NoArgsConstructor
public class MailUpgradeRankThread implements Runnable{

	@Autowired
	private EmailService emailService;
	
	private Ranking ranking;
	private GiftCode codeGenerated;
	private User user;
	
	public MailUpgradeRankThread() {
		System.out.println("init thread");
	}
	@Override
	public synchronized void run() {
		emailService.sendMailUPpgradeRank(ranking, user, codeGenerated);
	}
}
