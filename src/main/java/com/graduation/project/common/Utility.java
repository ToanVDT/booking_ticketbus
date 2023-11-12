package com.graduation.project.common;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.graduation.project.payload.response.SeatResponseForCustomer;

public class Utility {

	public static String RandomOrderCode() {
		int length = 9;
		String DigitString = "0123456789";
		StringBuilder s = new StringBuilder();
		String PREFIX = ConstraintMSG.PREFIX_ORDER_CODE;
		for(int i = 0; i < length ; i++) {
			int index = (int)(DigitString.length()*Math.random());
			s.append(DigitString.charAt(index));
		}
		return PREFIX+s.toString();
	}
	public static List<List<SeatResponseForCustomer>> getSeatWithTypeBus(List<SeatResponseForCustomer> listSeat, int col, int row) {
		int startIndex = 0;
		List<List<SeatResponseForCustomer>> resultList = new ArrayList<>();
		for (int i = 0; i < col; i++) {
			int endIndex = startIndex + row;
			if (i == col -1) {
				endIndex = listSeat.size();
			}
			List<SeatResponseForCustomer> subList = listSeat.subList(startIndex, endIndex);
			resultList.add(subList);
			startIndex = endIndex;
		}
		return resultList;
	}
	public static String formatMoneyWithCurrencyVN(Double price) {
		Locale locale = new Locale("vi", "VN");
		Currency currency = Currency.getInstance("VND");
		DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
		df.setCurrency(currency);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		numberFormat.setCurrency(currency);
		return numberFormat.format(price);
	}
}
