package com.example.spring10.repository;

import java.util.List;

import com.example.spring10.dto.CommentDto;

public interface CommentDao {
	public List<CommentDto> selectList(int parentNum);
	public int getSequence();
	public void insert(CommentDto dto);
	public void update(CommentDto dto);
	public void delete(int num);
	public CommentDto getByNum(int num);
	
}
