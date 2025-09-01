package com.example.spring08.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

@Controller
public class TestController {
	/*
	 * @ResponseBody 어노테이션을 붙인 컨트롤러 메소드에서 Map 을 리턴하면
	 * { } 형식의 json 문자열이 응답된다. 
	 * { } 안에는 Map 에 담긴 key : value 데이터가 들어있다.
	 */
	@GetMapping("/test/json01")
	@ResponseBody
	public Map<String, Object> json01(){
		Map<String, Object> map = Map.of(
				"num", 1,
				"name", "김구라",
				"isMan", true
				);
		
		return map;
	}
	
	
	// json 문자열 응답 테스트를 위한 클래스 ( dto 클래스 ) 
	@Data
	public class MemberResponse{
		private int num;
		private String name;
		private boolean isMan;
		
	}
	
	@GetMapping("/test/json02")
	@ResponseBody
	public MemberResponse json02() {
		MemberResponse m = new MemberResponse();
		m.setNum(2);
		m.setName("해골");
		m.setMan(false);
		
		return m;
	}
	
	@GetMapping("/test/json03")
	@ResponseBody
	public List<String> json03(){
		List<String> list=List.of("김구라", "해골", "원숭이");
		
		return list;
	}
	
	@GetMapping("/test/json04")
	@ResponseBody
	public List<MemberResponse> json04(){
		MemberResponse m1 = new MemberResponse();
		m1.setNum(1);
		m1.setName("김구라");
		m1.setMan(true);
		
		MemberResponse m2 = new MemberResponse();
		m2.setNum(2);
		m2.setName("해골");
		m2.setMan(false);
		
		List<MemberResponse> list =List.of(m1, m2);
		
		return list;
	}
}

