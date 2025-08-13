package com.example.spring02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring02.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;

// 이 클래스로 생성된 객체가 bean 이 되도록 어노테이션을 붙인다. 
// @Controller 어노테이션으로 bean 이 된 객체는 클라이언트 요청을 처리할 수 있다. 

@Controller
public class TestController {
	
	/* 매개 변수에 dto 를 선언하면 Spring 프레임 워크가 요청 파라미터를 모두 추출해서
	 * dto 의 필드명과 일치하면 알아서 dto의 setter 메소드를 호출해서 값을 넣은 
	 * dto 객체를 전달해준다
	 */
	@PostMapping("/member/add2")
	@ResponseBody
	public String memberAdd2(MemberDto dto) {
		System.out.println();
		return "회원정보를 추가했습니다";
	}
	
	@PostMapping("/member/add")
	@ResponseBody
	public String memberAdd(int num, String name, String addr) {
		
		System.out.println(num+" | "+name+" | "+addr);
		
		return "회원 정보를 추가했습니다";
	}
	/* @ReqeustParam 어노테이션을 이용해서 추출된 요청 파라미터를 전달받을 수 있다. 
	 * 매개변수명이 input 요소의 name 속성의 값과 일치한다면 @ReqeustParam 은 생략 가능 
	 * 
	 * index.html 의 폼에 있는 input 요소 <input name="msg"> 와 아래의 String msg 매개변수가 일치한다면 ㅇㅇ  
	 */
	@PostMapping("/send2")
	@ResponseBody
	public String send2(@RequestParam String msg) {
		
		return "/send okay!";
	}
	
	// HttpServletRequest 객체가 필요하다면 매개변수에 선언하면 객체가 전달된다.
	@PostMapping("/send") // Post 방식 매핑 어노테이션 
	@ResponseBody
	public String send(HttpServletRequest request) { 
		// 요청 파라미터 추출 
		String msg=request.getParameter("msg");
		// 콘솔창에 출력
		System.out.println(msg);
		return "/send okay!";
	}
	
	@GetMapping("/person-today") // GET 방식 "/person-today" 요청을 처리하겠다는 의미 
	@ResponseBody // 이 메소드에서 리턴해주는 문자열을 그대로(몸통에 씌워서) 클라이언트에게 응답하라는 의미 
	public String personToday() { // 지금은 단순한 string 문자열이지만 , list 일수도 있고 dto 일 수도 있다 
		
		return "오늘의 인물은 박준일";
	}
	
	// 링크를 누르는 것은 get 방식이다 따라서 GetMapping
	@GetMapping("/fortune")
	@ResponseBody
	public String fortune() {
		
		return "동쪽으로 가면 귀인을 만나요";
	}
	
}
