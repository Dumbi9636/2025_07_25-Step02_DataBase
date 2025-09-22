package com.example.spring10.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring10.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 의존 객체 생성자로 주입받기 위한 어노테이션 
@Repository
public class BoardDaoImpl implements BoardDao{

	
	// 의존객체 
	private final SqlSession session;
	
	
	@Override
	public List<BoardDto> selectPage(BoardDto dto) {
		/*
		 *  1. mapper's namespace : board 
		 *  2. sql's id : selectPage 
		 *  3. parameterType : BoardDto 
		 *  4. resultType : BoardDto ( select 된 row 하나를 어떤 type 으로 받을지를 결정해준다.)
		 */
		return session.selectList("board.selectPage", dto); // dto 에 담긴 keyword 가 있을 수도 잇고 null 일수도 있다 
															// keyword 가있다면 search 는 "title_content" or "title" or "writer"
	}
	
	
	// 새글을 작성하는 메소드
	@Override
	public void insert(BoardDto dto) {
		// 이 메소드를 호출하는 시점에 dto.num 은 0 이지만,
		session.insert("board.insert", dto);
		// 이 메소드가 리턴된 이후에는 dto.num 에는 저장된 글번호가 들어있다. 
		// ( mapper 가 실행되면서 글번호가 담기는것 selcetKey를 이용한 결과값이 dto 에 담긴다 ) 
		
		
	}

	@Override
	public BoardDto getByNum(int num) {
		
		return session.selectOne("board.getByNum", num);
	}
	
	@Override
	public int getCount(BoardDto dto) {
		// resultType : int
		return session.selectOne("board.getCount", dto);
	}
	
	@Override
	public BoardDto getByDto(BoardDto dto) {
		
		return session.selectOne("board.getByDto", dto);
	}

	
	// 글 수정하는 메소드
	@Override
	public int update(BoardDto dto) {
		// 수정된 row 의 갯수를 리턴 
		return session.update("board.update", dto);
		
	}
	
	
	// 글 삭제하는 메소드
	@Override
	public int delete(int num) {
		
		return session.delete("board.delete", num);
	}
}
