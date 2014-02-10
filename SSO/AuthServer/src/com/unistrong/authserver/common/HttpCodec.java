package com.unistrong.authserver.common;

import java.io.UnsupportedEncodingException;

public class HttpCodec {
	public static String encodeHttp(String text) throws UnsupportedEncodingException {
		return  java.net.URLEncoder.encode( text, "UTF-8");
	}
	
	public static String decodeHttp(String text) throws UnsupportedEncodingException {
		return  java.net.URLDecoder.decode(text, "UTF-8");
	}
}
