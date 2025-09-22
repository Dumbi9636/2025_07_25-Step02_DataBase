package com.example.spring10.exception;

public class UserNameException extends RuntimeException {
	
	public UserNameException(String msg) {
		super(msg);
	}
}
