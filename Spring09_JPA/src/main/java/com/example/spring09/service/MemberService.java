package com.example.spring09.service;

import java.util.List;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.dto.MemberListRequest;
import com.example.spring09.dto.MemberPageResponse;

public interface MemberService {
	public List<MemberDto> getAll();
	public MemberDto getMember(int num);
	public MemberDto addMember(MemberDto dto);
	public void updateMember(MemberDto dto);
	public MemberDto deleteMember(int num);
	// PageNum 을 전달하면 해당하는 페이지 번호를 리턴하는 메소드 
	//public MemberPageResponse getPage(int pageNum);
	// PageNum, condition, keyword 가 담겨있는 DTO를 전달하면 해당하는 페이지 번호, 검색조건, 키워드를 리턴하는 메소드 
	public MemberPageResponse getPage(MemberListRequest request);
	
}
