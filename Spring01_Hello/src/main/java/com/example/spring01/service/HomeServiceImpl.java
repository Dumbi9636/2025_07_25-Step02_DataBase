package com.example.spring01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* 스프링 프레임 워크가 HomeServiceImpl 클래스가 존재하는 패키지(com.example.spring01.service) 를 
 * component scan 을 하게 되면 적절한 어노테이션(@Component)이 붙어있는
 * 이 클래스로 객체를 생성해서 해당 객체의 참조값을 spring bean container 에서 관리하게 된다. 
 */


//Spring 이 component scan 을 해서 해당 클래스로 객체를 생성해서 관리하도록 어노테이션을 붙여놓는다.
@Component 
public class HomeServiceImpl implements HomeService {
	
	// 의존 객체 주입 받기 ( 구멍을 뚫기 위해 작성된 Drill interface 를 구현하기 위해 의존 객체를 주입 받는다) 
	@Autowired Drill d; 
	
	// 생성자
	public HomeServiceImpl() {
		System.out.println("HomeServiceImpl 객체가 생성됨!");
	}
	
	@Override
	public void clean(String name) {
		System.out.println(name+"의 집을 청소해요!");
	}

	@Override
	public void wash(String name) {
		System.out.println(name+"의 빨래를 빨아요~");
	}

	@Override
	public void hole(String name) {
		System.out.println(name+"에 구멍을 뚫어요 윙~~");
		// 주입받는 의존 객체를 이용해서 원하는 동작을 할 수 있다
		d.hole();
	}

}
