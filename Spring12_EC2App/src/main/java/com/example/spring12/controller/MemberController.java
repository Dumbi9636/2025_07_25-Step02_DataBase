package com.example.spring12.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring12.dto.MemberDto;
import com.example.spring12.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MemberController {
	
	// 의존객체 생성자 주입
	private final MemberService service;
	
	// 회원 조회
	@GetMapping("/members")
	public List<MemberDto> members(){
		
		return service.getAll();
	}
	
	// 회원 추가
	@PostMapping("/members")
	public ResponseEntity<Void> createMember(@RequestBody MemberDto dto){
		service.addMember(dto);
		return ResponseEntity.ok().build();
	}
}
