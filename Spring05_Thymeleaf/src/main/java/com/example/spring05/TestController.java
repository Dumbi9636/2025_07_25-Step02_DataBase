package com.example.spring05;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {
	
	// javascript 출력 테스트
	@GetMapping("/javascript")
	public String javascript(Model model) {
		//로그인여부
		model.addAttribute("isLogin", false);
		//나이
		model.addAttribute("age", 15);
		//이름
		model.addAttribute("name", "김구라");
		
		//회원 한명의 정보 
		MemberDto dto=MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		
		// 해당 데이터를 Model 객체에 담고 
		model.addAttribute("dto", dto);
		
		//DB 에서 select 한 결과라고 가정하자 
		MemberDto dto1=MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		MemberDto dto2=MemberDto.builder().num(2).name("해골").addr("행신동").build();
		MemberDto dto3=MemberDto.builder().num(3).name("원숭이").addr("상도동").build();
		
		// read only List 
		List<MemberDto> list=List.of(dto1, dto2, dto3);
		
		//Model 객체에 "list" 라는 키값으로 담기
		model.addAttribute("list", list);
		
		return "javascript";
	}
	
	// escape, unescape 테스트
	@GetMapping("/unescape")
	public String unescape(Model model) {
		// html 형식의 문자열을 template 페이지에 전달할 일도 있다. 
		// html 의 형식을 탈출해서 출력하는 것 
		String content="""
			<ul>
				<li>하나</li>
				<li>두울</li>
				<li>세엣</li>
			</ul>
		""";
		
		model.addAttribute("content", content);
		
		return "unescape";
	}
	
	// 숫자 출력을 위한 테스트 
	@GetMapping("/print-num")
	public String printNum(Model model) {
		
		// 테스트 위해 데이터 전달
		model.addAttribute("start", 6);
		model.addAttribute("end", 10);
		
		return "print-num";
	}
	
	// include 테스트
	@GetMapping("/include-test")
	public String includeTest(Model model) {
		// 테스트를 위한 데이터 전달 
		model.addAttribute("title", "오늘의 운세");
		model.addAttribute("content", "동쪽으로 가면 귀인을 밟아요");
		
		return "include-test";
	}
	
	
	/*	컨트롤러의 메소드에 UserDto 를 선언하면 폼 전송되는 파라미터가 자동으로 추출되어서 
	 * 	UserDto 의 필드에 담긴체로 전달된다. 
	 * 	
	 * 	@ModelAttribute 어노테이션을 이용하면 view page 에서 해당 객체에 담긴값을 활용할 수 있다. 
	 * 	Dto 에 있는 값을 모델객체에 키값으로 담아달라는 뜻임. 
	 */
	@PostMapping("/save")
	public String save(@ModelAttribute("dto") UserDto dto) {
		// "dto" 라는 키값으로 UserDto 객체가 Model 객체에 자동으로 담긴다
		// 클라이언트 폼에서 넘어온 파라미터들을 자동으로 DTO 필드에 바인딩해서 컨트롤러 메소드의 파라미터로 바로 받을 수 있음.
		// request.getParameter("name") 이런 걸 전혀 안 해도 되고, UserDto 객체에 바로 값이 들어감.
		return "save";
	}
	
	// form 을 통해 클라이언트에게 입력 받는 테스트
	@GetMapping("/form")
	public String form() {
		
		return "form";
	}
	
	
	@GetMapping("/if")
	public String liTest(Model model) {
		
		// view page 에서 if 문을 테스트할 값을 Model 객체에 담기 
		model.addAttribute("score", 75);
		model.addAttribute("age", 25);
		model.addAttribute("role", "staff");
		
		return "if";
	}
	
	
	@GetMapping("/member/list")
	public String memberList(Model model) {
		// DB 에서 불러온 회원 한명의 정보라고 가정하자
		MemberDto dto1 = MemberDto.builder().num(1).name("오잉").addr("노량진").build();
		MemberDto dto2 = MemberDto.builder().num(2).name("해골").addr("행신동").build();
		MemberDto dto3 = MemberDto.builder().num(3).name("에이콘").addr("강남역").build();
		List<MemberDto> list = List.of(dto1, dto2, dto3);
		// 응답에 필요한 데이터를 Model 객체에 담는다
		model.addAttribute("list", list);
		
		return "member/list";
	}
	
	
	@GetMapping("/member/detail")
	public String memberDetail(Model model) {
		
		/*
		MemberDto.MemberDtoBuilder builder=MemberDto.builder();
		builder.num(1);
		builder.name(null);
		builder.addr(null);
		MemberDto dto2 = builder.build();
		아래의 한줄 코딩과 같음 
		*/
		
		// DB 에서 불러온 회원 한명의 정보라고 가정하자
		MemberDto dto = MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		// 응답에 필요한 정보를 Model 객체에 담는다
		model.addAttribute("dto", dto);
		
		// /templates/member/detail.html  Thymeleaf 페이지로 응답하기 
		return "member/detail";
	}
}
