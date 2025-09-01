package com.example.spring08.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<String> notice = List.of("공지사항 입니다", "공지공지", "사항사항");
		// 응답에 필요한 정보를 model 객체에 담고
		model.addAttribute("notice", notice);
		
		// Thymeleaf 템플릿 페이지에서 응답하기 
		return "home";
	}
}
