package com.example.spring09.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.dto.MemberListRequest;
import com.example.spring09.dto.MemberPageResponse;
import com.example.spring09.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v2")
public class RestMemberController2 {
	
	private final MemberService memberService;
	
	// 회원 추가 요청 처리
	@PostMapping("/members")
	// @Valid 어노테이션을 추가해서 검증도 수행한다 
	public MemberDto create(@Valid @RequestBody MemberDto dto) { // json 문자열을 응답받을때는 @RequestBody 어노테이션이 들어가야한다. 
	//최초의 num 은 null 인데 insert 혹은 update 된다면 {"num":4, "name":"xxx", "addr":"yyy"} 이런 형식의 json 문서를 응답한다. 
		return memberService.addMember(dto);
	}
	
	
	//@GetMapping("/members")
	// pageNum 뿐만 아니라 검색조건, 키워드 까지 같이 가져올 수 있도록 매개변수를 추가해도 되지만 3개를 한번에 담을 수 있는 DTO 를 설계해도 된다.
	//public MemberPageResponse list(@RequestParam(defaultValue = "1") int pageNum) {
		// @ReqeustParam(defaultValue = "1") int pageNum
		// 만일 pageNum 이 넘어오지 않는다면 기본값 1을 설정한다 
		//return memberService.getPage(pageNum);
	@GetMapping("/members")
	public MemberPageResponse list(MemberListRequest request) {
		
		return memberService.getPage(request);
	}
	
	// 회원 정보 전체 수정요청 처리
	@PutMapping("/members/{num}")
	public MemberDto update(@PathVariable int num, @Valid  @RequestBody MemberDto dto) {
		// 수정할 회원의 번호를 dto 에 넣어준다.
		dto.setNum(num);
		memberService.updateMember(dto);
		// dto 에 업데이트 할 정보가 들어있어서 dto 자체를 리턴
		return dto; 
	}
}
