package com.example.spring10.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring10.dto.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao{
	
	// 의존객체 주입
	private final SqlSession session;
	
	// 원글의 글번호를 이용해서 원글에 달린 댓글 목록을 리턴하는 메소드
	@Override
	public List<CommentDto> selectList(int parentNum) {
		
		return session.selectList("comment.selectList", parentNum);
	}
	
	
	// 시퀀스를 미리 생성하는 메소드 
	@Override
	public int getSequence() {
		
		return session.selectOne("comment.getSequence");
	}

	
	// 댓글을 추가하는 메소드
	@Override
	public void insert(CommentDto dto) {
		session.insert("comment.insert", dto);
		
	}

		
	// 댓글을 수정하는 메소드
	@Override
	public void update(CommentDto dto) {
		session.update("comment.update", dto);
		
	}
	
	
	// 댓글을 삭제하는 메소드 
	@Override
	public void delete(int num) {
		session.delete("comment.delete", num);

	}


	@Override
	public CommentDto getByNum(int num) {
		
		return session.selectOne("comment.getByNum", num);
	}

}
