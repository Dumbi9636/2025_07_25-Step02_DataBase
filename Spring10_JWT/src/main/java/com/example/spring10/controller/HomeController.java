package com.example.spring10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HomeController {
	
	@GetMapping("/notice")
	public List<String> notice(){ // 공지사항은 로그인 없이 볼 수 있도록 Security config 에서 whitelist 에 추가 
		return List.of("React 로 app 구현중입니다", "니파바이러스감염증, 제1급 법정감염병 신규 지정 안내", "2025 내정보지킴이 캠페인");
	}
}
