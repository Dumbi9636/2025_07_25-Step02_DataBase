package com.example.spring12.service;

import java.util.List;

import com.example.spring12.dto.MemberDto;

public interface MemberService {
	public List<MemberDto> getAll();
	public MemberDto getMember(int num);
	public void addMember(MemberDto dto);
	public void updateMember(MemberDto dto);
	public void deleteMember(int num);
	
}
