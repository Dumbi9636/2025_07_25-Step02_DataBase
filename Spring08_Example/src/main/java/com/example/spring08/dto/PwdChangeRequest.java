package com.example.spring08.dto;

import lombok.Data;

@Data
public class PwdChangeRequest {
	private String userName;
	private String password;
	private String newPassword;
	
}
