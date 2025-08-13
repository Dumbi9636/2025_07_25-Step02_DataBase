package com.example.spring01.service;

import org.springframework.stereotype.Component;

// Spring 이 component scan 을 해서 해당 클래스로 객체를 생성해서 관리하도록 어노테이션을 붙여놓는다.
@Component
public class DrillImpl implements Drill { // Drill 이라는 인터페이스를 구현하는 DrillImpl

	@Override
	public void hole() {
		System.out.println("윙~~~");
	} 
	
}
