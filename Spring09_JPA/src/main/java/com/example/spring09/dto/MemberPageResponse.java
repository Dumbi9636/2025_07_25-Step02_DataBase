package com.example.spring09.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPageResponse {
	private List<MemberDto> list; // 회원목록
	private int startPageNum;
	private int endPageNum;
	private int totalPageCount;
	private int PageNum;
	
}
