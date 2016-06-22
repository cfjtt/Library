package com.just.bean;

import java.io.Serializable;

/**
 * 基本消息类,同来
 * @author Administrator
 * 
 */
public class BaseResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean flag;
	private String message;
	private Object result;

	public BaseResult() {
		super();
	}

	public BaseResult(Boolean flag, String message, Object result) {
		super();
		this.flag = flag;
		this.message = message;
		this.result = result;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BaseResult [flag=" + flag + ", message=" + message
				+ ", result=" + result + "]";
	}

}
