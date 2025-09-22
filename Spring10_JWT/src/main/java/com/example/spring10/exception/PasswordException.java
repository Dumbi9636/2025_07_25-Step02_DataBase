package com.example.spring10.exception;

// 기존 비밀번호가 일치하지 않았을때 throw 할 예외 클래스 
public class PasswordException extends RuntimeException {
	
	public PasswordException(String msg) {
		super(msg);
	}
}
