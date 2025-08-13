package com.example.spring01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.spring01.service.HomeService;
import com.example.spring01.service.HomeServiceImpl;

import jakarta.annotation.PostConstruct;

/*
 *	Spring 프레임워크를 사용해서 Java Apllication 을 만드는 이유 
 *	
 *	=> 유지보수를 편하게 하기 위해서
 *
 *  어떤 의미로 유지보수가 편하게 될까 ? 
 *  
 *  1. 핵심 의존 객체를 직접 생성(new)하지 않는다.
 *  	- 객체 생성과 관리를 Spring 프레임워크에 맡긴다 
 *  
 *  2. 필요한 객체가 있다면 Dependency Injection (DI) 방식을 받아서 사용한다. 
 *  
 *  3. Interface type (핵심 의존객체의 type) 을 적극 활용한다. 
 *  
 *  위의 세가지 원칙을 지키면 객체들 간의 의존관계가 느슨해져서 유지보수가 편하게 된다. 
 */

@SpringBootApplication // 이 어노테이션 때문에 Spring01HelloApplication 객체도 생성되고 관리가 된다
public class Spring01HelloApplication {
	
	// 필요한 핵심의존 객체를 필드로 선언하고 DI(주입) 되도록 설정한다.(Interface type 으로 선언한다)
	@Autowired HomeService service;
	
	// 이클래스로 객체가 생성된 이후에 호출하고 싶은 메소드에 @PostConstruct 메소드를 붙여 놓으면 된다. 
	@PostConstruct // 해당 Bean이 Spring Container에 의해 생성되고,  의존성 주입이 끝난 직후에 실행되는 메서드를 표시하는 어노테이션
	public void test() {
		service.clean("주뎅이");
		// 어딘가 구멍을 뚫고 싶다면?
		service.hole("바닥");
	}
	
	public static void main(String[] args) {
		// SpringApplication.run() 메소드를 호출하면 Spring 프레임워크가 준비된다. 
		ApplicationContext container = 
				SpringApplication.run(Spring01HelloApplication.class, args);
		
		/* "원숭이" 의 집을 청소해야 한다면 어떻게 해야할까? 
		 * 원숭이의 집을 청소하기 위한 핵심 객체는 HomeServiceimpl 객체\
		 * HomeServiceImpl service1 = new HomeServiceImpl();
		 * service1.clean("원숭이");
		 * 이 구조는 객체를 직접 생성했기 때문에 위의 세가지 원칙에 위배된다.  
		 */ 

		
		/* 그렇다면 Interface type을 활용한다는 것은 어떻게 하는 것일까
		 * spring 이 관리하는 객체중에 HomeService type 객체를 얻어낸다.
		 * Java 객체를 Bean 이라고 부른다.
		 * HomeServiceImpl 타입이 아닌 HomeService 즉 인터페이스 type을 사용하기 때문에
		 * HomeServiceImpl 을 삭제해도 에러가 나지 않는다. 
		 */ 
		HomeService service2 = container.getBean(HomeService.class);
		service2.clean("원숭이");
		// 어딘가 구멍을 뚫고 싶다면?
		service2.hole("바닥");
		
	}

}
