package com.zuriontech.web.test.david.zurionwebapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AppUtils {
	
	public static String maskName(String name) {
	    if (name == null || name.length() < 2) return "***";
	    return name.substring(0, 1) + "***";
	}
	
	public static String maskPhoneNumber(String phone) {
	    if (phone == null || phone.length() < 9) return "***";
	    return phone.substring(0, 6) + "***" + phone.substring(phone.length() - 3);
	}
	
	public static String hashPhoneNumber(String phone) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(phone.getBytes());
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Error hashing phone number", e);
	    }
	}

}
