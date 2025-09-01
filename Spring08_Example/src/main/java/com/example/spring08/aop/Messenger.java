package com.example.spring08.aop;


import org.springframework.stereotype.Component;

import com.example.spring08.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Messenger {
	
	public void sendGreeting(String msg) {
		//System.out.println("오늘의 인사:" + msg);
		log.debug("오늘의 인사:" + msg);
	}
	
	public void sendMember(MemberDto dto) {
		if( dto != null) {
			log.debug(dto.toString());
		}
	}

	public String getMessage() {
		log.debug("getMessage() 메소드가 수행됩니다.");
		return "열심히 공부 하자";
	}
}
