package com.example.spring08.service;

import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;

public interface BoardService {
	public BoardListResponse getBoardList(int pageNum, BoardDto dto);
	public void createContent(BoardDto dto);
	
	public BoardDto getDetail(BoardDto dto); // 글 하나에 대한 자세한 정보, 글 자세히 보기를 보여주기 위한 서비스 메소드
	public BoardDto getData(int num); // 수정할 글 정보를 보여주기 위한 서비스 메소드
	public void deleteContent(int num); // 게시글 삭제
	public void updateContent(BoardDto dto);  // 게시글 수정
}

