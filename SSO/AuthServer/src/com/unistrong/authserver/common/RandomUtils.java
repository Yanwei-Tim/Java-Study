package com.unistrong.authserver.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class RandomUtils {
	public static String generateToken(String userid) {
		String value = System.currentTimeMillis() + new Random().nextInt()
				+ userid;// 唯一指纹
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

	/**
	 * MD5加密算法
	 * 
	 * @param inputString
	 * @return
	 */
	public static String MD5(String inputString) {
		StringBuffer md5String = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					md5String.append("0");
				}
				md5String.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5String.toString();
	}
}
