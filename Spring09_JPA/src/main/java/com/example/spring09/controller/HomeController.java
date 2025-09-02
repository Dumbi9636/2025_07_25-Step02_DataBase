package com.example.spring09.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 어노테이션이 있어서 HomeController 객체는 자동 생성되고 bean 으로 관리된다
//bean 이된다는 의미는 Spring 이 관리하는 객체가 된다.
public class HomeController {
	
	// @Controller 어노테이션으로 bean 이 되는 객체는 클라이언트의 요청을 처리할 수 있다. 
	@GetMapping("/") // 최상위 경로 요청이 왔을때(lcoalhost:9000) 아래가 실행된다.
					 //컨트롤러 메소드의 매개변수에 Model 을 선언하면 객체가 자동으로 전달된다.
	public String home(Model model) {
		// 응답에 필요한 데이터는 Model 객체에 담는다 
		// Model 객체에 담으면 자동으로 HttpServletRequest 객체에 담긴다. 
		
		List<String> notice=List.of("공지사항 입니다!", "추석을 기다려요", "JSP는 끝났습니다");
		// "notice" 라는 키값으로 List<String> type 을 담는다. 
		
		model.addAttribute("notice", notice);
		// attAttribute 의 두번째 메소드의 객체는 object type 으로 int, string, dto, list 등 모든 걸 담을 수 있다. 
		
		/*	여기서 "home" 이라는 문자열을 리턴하면
		 * 	접두어로 "/WEB-INF/views/" 가 붙고  -> 이거는 application 의 properties 에 작성해둔 내용
		 * 	접미어로 ".jsp" 가 붙어서 
		 * 	"/WEB-INF/views/home.jsp 라는 view page 가 구성이된다.
		 * 	따라서 해당 jsp 페이지로 응답이 위임되어서 응답하게 되는 것이다. 
		 * 	home.jsp 페이지에서는 request 영역에 "notice" 라는 키값으로 담긴 데이터를 
		 * 	EL 표현식을 이용해서 추출할수 있다 -> ${reqeustScope.notice} 또는 ${notice}
		 */
		return "home";
		// 문자열 하나를 리턴하는데 이건 view page 를 리턴하게 된다. view page 로 foward 위임 
	}
}

