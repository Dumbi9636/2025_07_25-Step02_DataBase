package com.example.spring10.service;

import java.util.List;

import com.example.spring10.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> getComments(int parentNum); // 댓글 목록
	public void createComment(CommentDto dto); // 댓글 저장
	public void updateComment(CommentDto dto); // 댓글 수정
	public void deleteComment(int num); // 댓글 삭제
}
