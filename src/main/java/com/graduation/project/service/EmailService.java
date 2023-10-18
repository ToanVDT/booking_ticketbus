package com.graduation.project.service;

import com.graduation.project.payload.EmailDetails;

public interface EmailService {
	
	public void sendSimpleMail(EmailDetails email) throws Exception;
	public String sendMailWithAttachment(EmailDetails details);
}
