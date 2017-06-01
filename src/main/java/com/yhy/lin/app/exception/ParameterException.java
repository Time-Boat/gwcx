package com.yhy.lin.app.exception;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年6月1日 下午4:16:34
 */
public class ParameterException extends Exception {

	private String code = "";
	private String errorMessage = "";
	
//	public ParameterException() {
//	}

	public ParameterException(String message, String code) { // 用来创建指定参数对象
		this.code = code;
		this.errorMessage = message;
	}

	public String getCode() {
		return code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
