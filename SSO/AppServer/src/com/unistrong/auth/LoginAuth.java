package com.unistrong.auth;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.unistrong.auth.HttpRequest.HttpRequestException;

public class LoginAuth {
	
	//private static final String AUTH_URL_PASSWORD = "http://localhost:8080/AuthPassword";
	//private static final String AUTH_URL_TICKET = "http://localhost:8080/AuthTicket";
	
	public static final String AUTH_PARAM_UNAME = "userid";
	public static final String AUTH_PARAM_PASSWORD = "password";
	public static final String AUTH_PARAM_TICKET = "ticket";
	
	public static final String AUTH_PARAM_CODE = "authcode";
	public static final String AUTH_PARAM_DATA = "authdata";
	public static final String AUTH_PARAM_MESSAGE = "authmsg";
	
	private static final int CONNECT_TIMEOUT = 6000;
	private static final int READ_TIMEOUT = 6000;
	
	
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
	/*
	public static Result authByPassword(String uname, String password)
			throws UnsupportedEncodingException, HttpRequestException {
		String postData = String.format("%s=%s&%s=%s", AUTH_PARAM_UNAME, uname,
				AUTH_PARAM_PASSWORD, password);
		HttpRequest request = HttpRequest.post(AUTH_URL_PASSWORD).send(postData)
				.connectTimeout(CONNECT_TIMEOUT)
				.readTimeout(READ_TIMEOUT);
		if (request.code() != HttpURLConnection.HTTP_OK) {
			return new Result(-1, "�������쳣");
		}

		int code = Integer.parseInt(request.header(AUTH_PARAM_CODE));
		String data = HttpCodec.decodeHttp(request.header(AUTH_PARAM_DATA));
		String msg = HttpCodec.decodeHttp(request.header(AUTH_PARAM_MESSAGE));
		return new Result(code, data, msg);
	}
	*/
	
	 /**
     * �û���/ticket��֤
     * @param  authURL
     *       ��֤URL
     * @param  uname
     *        �û���
     * @param  ticket
     *         ��½ƾ��
     * @return  ����Result�������code��message��data
	 * @throws HttpRequestException 
	 * @throws UnsupportedEncodingException 
     */
	public static Result authByTicket(String authURL, String uname, String ticket) throws UnsupportedEncodingException, HttpRequestException{
		String postData = String.format("%s=%s&%s=%s", AUTH_PARAM_UNAME, uname,
				AUTH_PARAM_TICKET, HttpCodec.encodeHttp(ticket));  // ����encodeHttp��ӦΪbase64�к���+���� URL ����ʱ�ᱻ���ɿո�
		HttpRequest request = HttpRequest.post(authURL).send(postData)
				.connectTimeout(CONNECT_TIMEOUT)
				.readTimeout(READ_TIMEOUT);
		
		if (request.code() != HttpURLConnection.HTTP_OK){
			return new Result(-1, "�������쳣");
		}

		int code = Integer.parseInt(request.header(AUTH_PARAM_CODE));
		String msg = HttpCodec.decodeHttp(request.header(AUTH_PARAM_MESSAGE));
		return new Result(code, msg);
	}
	
	/**
	 * ���Դ���
	 */
//	 public static void main(String args[]) { 
//			String uname = "compass1";
//			String ticket = "YXSfpTvcO+LlkRsr2FOZ/g==";
//			try {
//				Result result = LoginAuth.authByTicket("http://cas.min.com:8080/AuthTicket", uname, ticket);
//				System.out.println(result.toProtocolString());
//			} catch (UnsupportedEncodingException | HttpRequestException e) {
//				e.printStackTrace();
//			}
//	    } 
}
