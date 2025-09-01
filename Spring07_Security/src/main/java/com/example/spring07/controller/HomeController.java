package com.example.spring07.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		
		return "home";
	}
	
	@GetMapping("/study")
	@ResponseBody
	public String study() {
		// 컨트롤러나 서비스에서 로그인된 userName 을 알고 싶을때 
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("로그인된 userName:"+userName);
		
		return "let's study!";
	}
}
