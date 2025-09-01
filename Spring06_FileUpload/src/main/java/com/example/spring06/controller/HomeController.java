package com.example.spring06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		
		// /templates/home.html 타임리프 페이지로 응답하겠다는 의미
		return "home";
	}
}
