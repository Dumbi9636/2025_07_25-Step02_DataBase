package com.example.spring09.controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// 컨트롤러에서 리턴하는 데이터를 json 으로 응답하고자 할때 사용하는 어노테이션
@RequestMapping("/v1") // @GetMapping("/v1/member/hello") 에서 접두어 v1 을 위에 @ReqeustMapping 으로 선언해놓으면 밑에서 생략이 가능함.
@RestController
@RequiredArgsConstructor
@Tag(name = "Members", description = "회원관리 API") //swagger ui 에 제공할 정보 
public class RestMemberController {
	/*
	 *	@RestController 는 컨트롤러에서 리턴한 List 나 dto 를 json 문자열로 변경해서 응답한다
	 *	
	 *	List 는 [ ] 로 변경
	 *	Map 이나 dto 는 { } 로 변경
	 *	List<dto> 는 [{},{},{}...] 의 형식으로 변경 
	 *	
	 *	dto 의 필드 구성에 따라 좀더 복잡한 형식의 {} 가 구성될수도 있다
	 *	 { 
	 *		"num" : 1,
	 *		"name" : "kim",
	 *		"hobby" : ["xxx", "yyy"], -> object 나 또다른 배열 형태가 들어있을 수도 있다	
	 *		"info" : { } 
	 *	 }
	 */ 
	
	
	// 의존성 주입
	private final MemberService memberService;
	
	
	
	// 실제 RESTful API 를 만들어보자 	
	
	// 회원 목록 요청 처리
	@Operation(summary = "회원 목록 조회", description ="전체 회원을 페이징 없이 반환합니다.")  // @Operation 어노테이션을 붙여서 컨트롤러메소드 정보를 swagger-ui 에서 제공할 수 있다.
	@GetMapping("/members")
	public List<MemberDto> list(){
		List<MemberDto> list=memberService.getAll();
		
		return list;
	}
	
	
	// 회원 추가 요청 처리
	@PostMapping("/members")
	public MemberDto create(@RequestBody MemberDto dto) { // json 문자열을 응답받을때는 @RequestBody 어노테이션이 들어가야한다. 
		//최초의 num 은 null 인데 insert 혹은 update 된다면 {"num":4, "name":"xxx", "addr":"yyy"} 이런 형식의 json 문서를 응답한다. 
		return memberService.addMember(dto);
	}
	
	
	// 회원 한명의 정보 요청 처리 
	@GetMapping("/members/{num}")
	public MemberDto detail(@PathVariable int num) {
		
		return memberService.getMember(num);
	}
	
	
	// 회원 정보 삭제 요청 처리
	@DeleteMapping("/members/{num}")
	public MemberDto delete(@PathVariable int num) {
		
		return memberService.deleteMember(num);
	}
	
	
	// 회원 정보 전체 수정요청 처리
	@PutMapping("/members/{num}")
	public MemberDto update(@PathVariable int num, @RequestBody MemberDto dto) {
		// 수정할 회원의 번호를 dto 에 넣어준다.
		dto.setNum(num);
		memberService.updateMember(dto);
		// dto 에 업데이트 할 정보가 들어있어서 dto 자체를 리턴
		return dto; 
	}
	
	
	
	// RESTful API 테스트 
	// @RestController 는 view page 로 리턴하지 않고 리턴하는 내용을 클라이언트에게 그대로 제공한다.
	@GetMapping("/member/hello")
	public String hello() {
		
		return "hello, world!";
		
	}
	
	
	// Dto 는 json 문자열로 변환되어 전환된다.
	@GetMapping("/member/hello2")
	public MemberDto hello2() {
		
		return MemberDto.builder().num(1).name("김구라").addr("노량진").build();
	}
	
	 
	// List 는 배열로 변환되어 전환된다. 
	@GetMapping("/member/hello3")
	public List<String> hello3(){
		
		return List.of("김구라, 해골, 원숭이"	);
	}
	
	
}
