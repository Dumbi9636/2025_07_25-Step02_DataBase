package com.example.spring03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FortuneController {
	/*	
	 * HttpservletReqeust 는 HTTP 의 모든 기능을 다루는 객체
	 * Model 은 view page 로 넘길 데이터만 담는 객체 ( 더 편리하게 사용할 수 있다) 
	 * Model 객체도 컨트롤러 메소드의 매개변수에 선언만하면 자동으로 spring 이 전달해 준다. 
	 */
	@GetMapping("/fortune2")
	public String fortune2(Model model) {
		String fortune = "토요일 제발 비오지마라! 토요일 제발 비오지마라!";
		
		// Model 객체에 담으면 자동으로 HttpServletReqeust 객체에 담긴다
		model.addAttribute("fortune", fortune);
	
		return "fortune";
	}
	
	@GetMapping("/fortune")
	public String fortune(HttpServletRequest request) {
		
		// DB 에서 읽어온 오늘의 운세라고 가정하자 
		String fortune = "대박 운세! 좋은 일이 가득합니다.";
				
		request.setAttribute("fortune", fortune);
		
		/* @ResponseBody 어노테이션 없이 리턴해주는 문자열은 view page(jsp페이지)의 위치를 의미한다
		 * 
		 * "fortune" 을 리턴하면
		 * 접두어(prefix) 에 "/WEB-INF/views/" 가 붙고
		 * 접미어(suffix) 에 ".jsp 가 자동으로 붙어서 
		 * 결국 view page 는 "/WEB-INF/views/fortune.jsp" 를 가리키는 것이다.
		 * 해당 jsp 페이지로 응답이 위임된다(forward 이동) 
		 */
		return "fortune";
	}
}
