package com.example.spring03.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	
	
	/* http://localhost:9000/
	 * 위에 처럼 이서버의 최상위(root) 경로 요청이 왔을때 요청을 처리할 컨트롤러 메소드 
	 */
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		// DB 에서 읽어온 공지사항이라고 가정하자
		List<String> notice=List.of("Spring 프레임워크가 시작되었습니다", "어쩌구", "저쩌구");
		
		// 응답에 필요한 data 를 HttpServletRequest 객체에 담아준다 
		request.setAttribute("notice", notice);
		
		/* @ResponseBody 어노테이션 없이 리턴해주는 문자열은 view page(jsp페이지)의 위치를 의미한다
		 * 
		 * "home" 을 리턴하면
		 * 접두어(prefix) 에 "/WEB-INF/views/" 가 붙고
		 * 접미어(suffix) 에 ".jsp 가 자동으로 붙어서 
		 * 결국 view page 는 "/WEB-INF/views/home.jsp" 를 가리키는 것이다.
		 * 해당 jsp 페이지로 응답이 위임된다(forward 이동) 
		 */
		return "home";
	}
}
