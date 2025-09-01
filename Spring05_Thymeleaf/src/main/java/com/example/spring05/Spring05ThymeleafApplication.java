package com.example.spring05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Spring05ThymeleafApplication {
    
	
	// 이 클래스로 객체가 생성된 직후 호출될 메소드에 붙이는 어노테이션
	@PostConstruct
	public void test() {
		MemberDto dto = new MemberDto();
		dto.setNum(1);
		dto.setName("김구라");
		dto.setAddr("노량진");
		System.out.println(dto);
	
		// @Builder 의 기능을 이용해서 MemberDto 객체 얻어내기(한줄 코딩, .build()까지 해주면 dto 자체를 리턴해준다) 
		MemberDto dto2 = MemberDto.builder().num(2).name("해골").addr("행신동").build()	;
		System.out.println(dto2);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(Spring05ThymeleafApplication.class, args);
	}

}
