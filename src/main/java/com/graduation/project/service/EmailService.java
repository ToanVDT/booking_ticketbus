package com.graduation.project.service;

import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.User;
import com.graduation.project.payload.EmailDetails;
import com.graduation.project.payload.request.MailOrderStatusRequest;
import com.graduation.project.payload.request.MailSendInformOrderToBrandOwnerRequest;

public interface EmailService {
	
	public void sendSimpleMail(EmailDetails email) throws Exception;
	public String sendMailWithAttachment(EmailDetails details);
	public void sendMailUPpgradeRank(Ranking ranking, User user, GiftCode giftCode);
	public void sendMailWelcomNewJoiner(Ranking ranking, User user, GiftCode giftCode);
	public void sendMailOrderStatus(MailOrderStatusRequest request,User user);
	public void sendMailInformOrderToBrandOwner(MailSendInformOrderToBrandOwnerRequest request,User user);
}
