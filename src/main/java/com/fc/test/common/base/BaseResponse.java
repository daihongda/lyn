package com.fc.test.common.base;

import java.io.Serializable;

public class BaseResponse implements Serializable{

	private static final long serialVersionUID = 3689430194989167438L;

	public static Boolean SUCCESS = true;
	
	public static Boolean FAIL = false;
	//返回标识 true / false
	private Boolean success;
	
	//返回数据
	private Object data;

	//失败信息
	private String msg;

	// 返回code
	private String code;

	public BaseResponse(Boolean success, Object data, String msg) {
		super();
		this.success = success;
		this.data = data;
		this.msg = msg;
	}

	public BaseResponse(Boolean success, Object data, String msg, String code) {
		super();
		this.success = success;
		this.data = data;
		this.msg = msg;
		this.code = code;
	}
	
	/**
	 * 直接返回成功
	 */
	public BaseResponse() {
		super();
		this.success = true;
	}
	
	/**
	 * 返回成功 + 数据
	 * @param success BaseResponse.SUCCESS
	 * @param obj
	 */
	public BaseResponse(Boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}
	
	/**
	 * 返回失败 + 失败信息
	 * @param msg
	 */
	public BaseResponse(String msg) {
		super();
		this.success = FAIL;
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static Boolean getSUCCESS() {
		return SUCCESS;
	}

	public static void setSUCCESS(Boolean SUCCESS) {
		BaseResponse.SUCCESS = SUCCESS;
	}

	public static Boolean getFAIL() {
		return FAIL;
	}

	public static void setFAIL(Boolean FAIL) {
		BaseResponse.FAIL = FAIL;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
