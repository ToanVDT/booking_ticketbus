package com.graduation.project.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.graduation.project.common.Utility;
import com.graduation.project.entity.GiftCode;
import com.graduation.project.entity.Ranking;
import com.graduation.project.entity.User;
import com.graduation.project.payload.EmailDetails;
import com.graduation.project.payload.request.MailOrderStatusRequest;
import com.graduation.project.payload.request.MailSendInformOrderToBrandOwnerRequest;
import com.graduation.project.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender emailSender;

	@Autowired
	SpringTemplateEngine templateEngine;

	@Override
	public void sendSimpleMail(EmailDetails email) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
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

	@Override
	public void sendMailUPpgradeRank(Ranking ranking, User user, GiftCode giftCode) {
		EmailDetails email = new EmailDetails();
		try {
			Map<String, Object> properties = new HashMap<>();
			email.setSubject("[VEXERE: Gửi mã quà tặng]");
			properties.put("rankName", ranking.getRankName());
			properties.put("customerFirstName", user.getFirstName());
			properties.put("giftcode", giftCode.getGiftCode());
			properties.put("expireDate", giftCode.getExpireDate());
			String giftMoney = Utility.formatMoneyWithCurrencyVN(ranking.getMoneyReduced());
			properties.put("giftMoney", giftMoney);

			email.setFrom("fromemail@gmail.com");
			email.setTemplate("MailInFormRank.html");
			email.setProperties(properties);
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(user.getEmail());
			if (matcher.matches()) {
				email.setTo(user.getEmail());
				sendSimpleMail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailWelcomNewJoiner(Ranking ranking, User user, GiftCode giftCode) {
		EmailDetails email = new EmailDetails();
		try {
			Map<String, Object> properties = new HashMap<>();
			email.setSubject("[VEXERE: Chào thành viên mới]");
			properties.put("customerFirstName", user.getFirstName());
			properties.put("giftcode", giftCode.getGiftCode());
			properties.put("expireDate", giftCode.getExpireDate());
			String giftMoney = Utility.formatMoneyWithCurrencyVN(ranking.getMoneyReduced());
			properties.put("giftMoney", giftMoney);
			email.setFrom("fromemail@gmail.com");
			email.setTemplate("MailNewJoiner.html");
			email.setProperties(properties);
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(user.getEmail());
			if (matcher.matches()) {
				email.setTo(user.getEmail());
				sendSimpleMail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailOrderStatus(MailOrderStatusRequest request, User user) {
		EmailDetails email = new EmailDetails();
		try {
			Map<String, Object> properties = new HashMap<>();
			email.setSubject("[VEXERE:Thông báo trạng thái đơn đặt]");
			properties.put("customerName", request.getCustomerName());
			properties.put("brandName", request.getBrandName());
			properties.put("pickUpPoint", request.getPickUpPoint());
			properties.put("orderCode", request.getOrderCode());
			properties.put("routeName", request.getRouteName());
			properties.put("dropOffPoint", request.getDropOffPoint());
			properties.put("tickets", request.getListSeatOrderd());
			String totalPrice = Utility.formatMoneyWithCurrencyVN(request.getTotalPrice());
			properties.put("totalPrice", totalPrice);
			properties.put("orderStatus", request.getOrderStatus());
			properties.put("paymentStatus", request.getPaymentStatus());
			properties.put("bookingDate", request.getBookingDate());
			properties.put("datetimeTravel", request.getDateTimeTravel());
			email.setFrom("fromemail@gmail.com");
			email.setTemplate("MailStatusOrder.html");
			email.setProperties(properties);
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(user.getEmail());
			if (matcher.matches()) {
				email.setTo(user.getEmail());
				sendSimpleMail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailInformOrderToBrandOwner(MailSendInformOrderToBrandOwnerRequest request, User user) {
		EmailDetails email = new EmailDetails();
		try {
			Map<String, Object> properties = new HashMap<>();
			email.setSubject("[VEXERE: Thông báo đơn đặt từ khách hàng]");
			properties.put("customerName", request.getCustomerName());
			properties.put("brandName", request.getBrandName());
			properties.put("pickUpPoint", request.getPickUpPoint());
			properties.put("routeName", request.getRouteName());
			properties.put("customerPhone", request.getCustomerPhone());
			properties.put("customerEmail", request.getCustomerEmail());
			properties.put("dropOffPoint", request.getDropOffPoint());
			properties.put("tickets", request.getListSeatOrderd());
			String totalPrice = Utility.formatMoneyWithCurrencyVN(request.getTotalPrice());
			properties.put("totalPrice", totalPrice);
			properties.put("orderStatus", request.getOrderStatus());
			properties.put("paymentStatus", request.getPaymentStatus());
			properties.put("bookingDate", request.getBookingDate());
			properties.put("datetimeTravel", request.getDateTimeTravel());
			email.setFrom("fromemail@gmail.com");
			email.setTemplate("notification.html");
			email.setProperties(properties);
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(user.getEmail());
			if (matcher.matches()) {
				email.setTo(user.getEmail());
				sendSimpleMail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
