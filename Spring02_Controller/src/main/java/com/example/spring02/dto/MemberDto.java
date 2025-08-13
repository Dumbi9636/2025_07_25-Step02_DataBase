package com.example.spring02.dto;

import lombok.Getter;
import lombok.Setter;

// lombok 의 기능을 이용해서 setter, getter 메소드가 만들어지게한다. 
@Setter
@Getter
public class MemberDto {
	private int num;
	private String name;
	private String addr;
	
	
}
