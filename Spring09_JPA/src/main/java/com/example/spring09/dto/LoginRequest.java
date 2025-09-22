package com.example.spring09.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequest {
	private String userName;
	private String password;
	
}
