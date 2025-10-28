package com.example.spring10.service;

import com.example.spring10.dto.CommentDto;
import com.example.spring10.dto.CommentListResponse;

public interface CommentService {
	public CommentListResponse getComments(int parentNum, int pageNum);
	public void createComment(CommentDto dto); //댓글 저장 
	public void updateComment(CommentDto dto); //댓글 수정
	public void deleteComment(int num); //댓글 삭제
}