package com.example.spring08.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 응답에 필요한 데이터를 하나의 객체에 담을 수 있도록 클래스를 설계한다.
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class BoardListResponse {
	// 글목록
	private List<BoardDto> list;
	
	private int startPageNum;
	private int endPageNum;
	private int totalPageCount;
	private int pageNum;
	private int totalRow;
	private String keyword; // 검색키워드
	private String search; // 검색 조건
	private String query; // GET 방식 파라미터 search-xxx&keyword=xxx 형식 or "" 
						  // 작성자 검색에 kimgura 를 입력했다면 
						   // search=writer&keyword=kimgura
						  // 제목검색에 hello 가 있다면 
						  // search=title&keyword=hello
	
}
