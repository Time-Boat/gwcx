package com.yhy.lin.app.exception;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年6月1日 下午4:16:34
 */
public class TokenException extends Exception {

	public TokenException() {
	}

	public TokenException(String message) { // 用来创建指定参数对象
		super(message); // 调用超类构造器
	}
}
