package com.unistrong.auth;

import java.io.UnsupportedEncodingException;

public class HttpCodec {
	public static String encodeHttp(String text) throws UnsupportedEncodingException {
		if (text == null)
			return null;
		return  java.net.URLEncoder.encode( text, "UTF-8");
	}
	
	public static String decodeHttp(String text) throws UnsupportedEncodingException {
		if (text == null)
			return null;
		return  java.net.URLDecoder.decode(text, "UTF-8");
	}
}
