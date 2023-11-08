package com.graduation.project.service.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.graduation.project.payload.EmailDetails;
import com.graduation.project.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	SpringTemplateEngine templateEngine;
	
	@Override
	public void sendSimpleMail(EmailDetails email) throws Exception {
		 MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        context.setVariables(email.getProperties());
	        helper.setFrom(email.getFrom());
	        helper.setTo(email.getTo());
	        helper.setSubject(email.getSubject());
	        String html = templateEngine.process(email.getTemplate(), context);
	        helper.setText(html, true);
	        emailSender.send(message);
			System.out.println("Mail Send...");
		
	}

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		// TODO Auto-generated method stub
		return null;
	}

}
