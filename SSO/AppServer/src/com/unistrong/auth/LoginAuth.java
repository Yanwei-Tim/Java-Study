package com.unistrong.auth;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.unistrong.auth.HttpRequest.HttpRequestException;

public class LoginAuth {
	
	private static final String AUTH_URL_PASSWORD = "http://localhost:8080/AuthPassword";
	private static final String AUTH_URL_TICKET = "http://localhost:8080/AuthTicket";
	
	public static final String AUTH_PARAM_UNAME = "userid";
	public static final String AUTH_PARAM_PASSWORD = "password";
	public static final String AUTH_PARAM_TICKET = "ticket";
	
	public static final String AUTH_PARAM_CODE = "authcode";
	public static final String AUTH_PARAM_DATA = "authdata";
	public static final String AUTH_PARAM_MESSAGE = "authmsg";
	
	
	 /**
     * �û���/������֤
     * @param  uname
     *        �û���
     * @param  password
     *         ����
     * @return  ����Result�������code��message��data
	 * @throws HttpRequestException 
	 * @throws UnsupportedEncodingException 
     */
	public static Result authByPassword(String uname, String password) throws UnsupportedEncodingException, HttpRequestException{
		String url = String.format("%s?%s=%s&%s=%s", AUTH_URL_PASSWORD, AUTH_PARAM_UNAME, uname, AUTH_PARAM_PASSWORD, password);
		HttpRequest request = HttpRequest .get(url);
		if (request.code() != HttpURLConnection.HTTP_OK){
			return new Result(-1, "�������쳣");
		}
		
		int code = Integer.parseInt(request.header(AUTH_PARAM_CODE));
		String data = HttpCodec.decodeHttp(request.header(AUTH_PARAM_DATA));
		String msg = HttpCodec.decodeHttp(request.header(AUTH_PARAM_MESSAGE));
		return new Result(code, data, msg);
	} 
	
	 /**
     * �û���/ticket��֤
     * @param  uname
     *        �û���
     * @param  ticket
     *         ��½ƾ��
     * @return  ����Result�������code��message��data
	 * @throws HttpRequestException 
	 * @throws UnsupportedEncodingException 
     */
	public static Result authByTicket(String uname, String ticket) throws UnsupportedEncodingException, HttpRequestException{
		String url = String.format("%s?%s=%s&%s=%s", AUTH_URL_TICKET, AUTH_PARAM_UNAME, uname, AUTH_PARAM_TICKET, ticket);
		HttpRequest request = HttpRequest .get(url);
		if (request.code() != HttpURLConnection.HTTP_OK){
			return new Result(-1, "�������쳣");
		}

		int code = Integer.parseInt(request.header(AUTH_PARAM_CODE));
		String msg = HttpCodec.decodeHttp(request.header(AUTH_PARAM_MESSAGE));
		return new Result(code, msg);
	}
}
