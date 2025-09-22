package com.example.spring09.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.LoginRequest;

@RequestMapping("/v1")
@RestController
public class RestUserController {
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request){
		boolean isValid = request.getUserName().equals("kimgura") && request.getPassword().equals("1234");
		// 만일 유효하지 않는다면
		if(!isValid) {
			// 401 을 응답한다
			ResponseEntity<String> entity=
					ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 틀려요");
			return entity;
		}
		// 가상의 토큰이라고 생각하자 
		String token="api 자유 이용권, 유효기간:2025.9.16, userName"+request.getUserName();
		// 정상응답을 하면서 token 을 발급한다 
		ResponseEntity<String> entity = ResponseEntity.ok(token);
		return entity;
	}
}
