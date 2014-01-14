package com.unistrong.common;
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Random; 

import sun.misc.BASE64Encoder;

public class RandomUtils {
	   public static  String generateToken(String userid){
	        String value = System.currentTimeMillis()+new Random().nextInt()+userid;//Î¨Ò»Ö¸ÎÆ
	        try {  
	            MessageDigest md = MessageDigest.getInstance("md5");
	            byte[] b = md.digest(value.getBytes());
	            BASE64Encoder be = new BASE64Encoder();
	            be.encode(b);
	            return be.encode(b);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
