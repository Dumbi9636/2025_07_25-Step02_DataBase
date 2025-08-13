package com.example.spring03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FortuneController {
	
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
