package com.example.spring08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.example.spring08.aop.Messenger;
import com.example.spring08.dto.MemberDto;
import com.example.spring08.test.WritingUtil;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource(value="classpath:custom.properties")
@SpringBootApplication
public class Spring08ExampleApplication {
	
	// 의존 객체 주입받기 
	@Autowired
	private WritingUtil util;
	
	@Autowired
	private Messenger m;  // Messenger 주입
	
	// spring 프레임워크가 준비되고 난 이후에 호출할 메소드 
	@PostConstruct
	public void testAop() {
		//util.writeLetter();
		//util.writeReport();
		//util.writeDiary();
		
		// 오늘의 인사가 출력 안된다
		m.sendGreeting("안녕 똥깨야!");
		// 오늘의 인사가 출력된다
		m.sendGreeting("안녕 아무개야!");
		
		
		MemberDto dto = MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		m.sendMember(dto);
		
		
		String msg=m.getMessage();
		//System.out.println("리턴된 msg:" +msg);
		log.debug("리턴된 msg" +msg);
	}
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(Spring08ExampleApplication.class, args);
	}

}
