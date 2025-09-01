package com.example.spring04.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring04.dto.BookDto;

@Repository
public class BookDaoImpl implements BookDao {
	
	// 필요한 session 객체 
	private final SqlSession session;
	
	// 생성자 주입 
	public BookDaoImpl(SqlSession session) {
		this.session=session;	
	}

	
	
	@Override
	public List<BookDto> selectAll() {
		List<BookDto> list = session.selectList("book.selectAll");
		return list;
	}

	@Override
	public void insert(BookDto dto) {
		session.insert("book.insert", dto);
	}

	@Override
	public int update(BookDto dto) {
	
		return session.update("book.update", dto);
	}

	@Override
	public int deleteByNum(int num) {
	
		return session.delete("book.delete", num);
	}

	@Override
	public BookDto getByNum(int num) {
		
		BookDto dto=session.selectOne("book.getByNum", num);
		return dto;
	}
}
