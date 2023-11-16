package com.graduation.project.service;

import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.User;
import com.graduation.project.payload.EmailDetails;
import com.graduation.project.payload.request.MailOrderStatusRequest;
import com.graduation.project.payload.request.MailSendInformOrderToBrandOwnerRequest;

public interface EmailService {

	void sendSimpleMail(EmailDetails email) throws Exception;

	String sendMailWithAttachment(EmailDetails details);

	void sendMailUPpgradeRank(Ranking ranking, User user, GiftCode giftCode);

	void sendMailWelcomNewJoiner(Ranking ranking, User user, GiftCode giftCode);

	void sendMailOrderStatus(MailOrderStatusRequest request, User user);

	void sendMailInformOrderToBrandOwner(MailSendInformOrderToBrandOwnerRequest request, User user);

	void sendMailThanksLeter(String customerName, String brandName, User user);
	
	void sendMailForgotPassword(User user,String validateCode);
}
