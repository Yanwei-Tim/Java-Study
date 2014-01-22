package com.unistrong.auth;

import java.io.Serializable;

public class Result implements Serializable {
	private static final long serialVersionUID = 195096680823819268L;
	private Integer code;
	private String message;
	private Object data;

	public static Result OK = new Result(0, "OK");
	public static Result ERROR = new Result(-1, "SYSTEM ERROR");

	public Result() {
	}

	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Result(Integer code, Object data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toProtocolString(){
		return String.format("code=%d\r\ndata=%s\r\nmsg=%s", this.code,this.data, this.message);
	}
	
}
