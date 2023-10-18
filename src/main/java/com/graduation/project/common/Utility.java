package com.graduation.project.common;

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
}
