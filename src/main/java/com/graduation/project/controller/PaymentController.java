package com.graduation.project.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.project.common.VNPayConst;
import com.graduation.project.payload.request.PayRequest;
import com.graduation.project.payload.response.APIResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/payment")
public class PaymentController {

//	@Autowired
//	private VNPayService payService; 
    @GetMapping("payment-callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams,HttpServletResponse response) throws IOException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
            response.sendRedirect("http://localhost:4200/payment-success");

            } else {
                response.sendRedirect("http://localhost:4200/payment-failed");
            }
        

    }
	
	@PostMapping("create-pay")
	public ResponseEntity<?> createPayment(@RequestBody PayRequest requestParams , HttpServletRequest request) throws IOException{

		double amountDouble = requestParams.getAmount();
		int amount = (int)amountDouble*100;
		Map<String,String> vnp_params = new HashMap<>();
		vnp_params.put("vnp_Version",VNPayConst.vnp_Version);
		vnp_params.put("vnp_Command", VNPayConst.vnp_Command);
		vnp_params.put("vnp_TmnCode", VNPayConst.vnp_TmnCode);
		vnp_params.put("vnp_Amount", String.valueOf(amount));
		String bank_code = requestParams.getBankCode();
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_params.put("vnp_BankCode", bank_code);
		}
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String vnp_CreateDate = time.format(formatter);
		vnp_params.put("vnp_CreateDate",vnp_CreateDate);
		vnp_params.put("vnp_CurrCode",VNPayConst.vnp_CurrCode);
		vnp_params.put("vnp_IpAddr",VNPayConst.getIpAddress(request));
		vnp_params.put("vnp_Locale",VNPayConst.vnp_Locale);
		vnp_params.put("vnp_OrderInfo", "Thanh toan tien");
		vnp_params.put("vnp_OrderType", VNPayConst.vnp_OrderType);
		vnp_params.put("vnp_ReturnUrl",VNPayConst.vnp_ReturnUrl);
		vnp_params.put("vnp_TxnRef", VNPayConst.getOTP(8));
//		vnp_params.put("vnp_SecureHash",PaymentConfig.vnp_SecureHash);
		List fieldName = new ArrayList(vnp_params.keySet());
		Collections.sort(fieldName);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator iterator = fieldName.iterator();
		while (iterator.hasNext()){
			String name = (String) iterator.next();
			String value = vnp_params.get(name);
			if ((value != null)&&(value.length()>0)){

				hashData.append(name);
				hashData.append("=");
				hashData.append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));

				query.append(URLEncoder.encode(name,StandardCharsets.US_ASCII.toString()));
				query.append("=");
				query.append(URLEncoder.encode(value,StandardCharsets.US_ASCII));

				if (iterator.hasNext()){
					query.append("&");
					hashData.append("&");
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPayConst.hmacSHA512(VNPayConst.vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNPayConst.vnp_PayUrl + "?" + queryUrl;
		System.out.println(paymentUrl);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("payment",paymentUrl,true));
	}
}
