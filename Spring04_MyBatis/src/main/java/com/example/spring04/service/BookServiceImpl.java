package com.example.spring04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring04.dto.BookDto;
import com.example.spring04.repository.BookDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	// 생성자 주입
	private final BookDao dao;
	
	
	@Override
	public List<BookDto> getAll() {

		return dao.selectAll();
	}

	@Override
	public BookDto getBook(int num) {
		BookDto dto = dao.getByNum(num);
		if(dto == null) {
			
		}
		
		return dto;
	}

	@Override
	public void addBook(BookDto dto) {
		
		dao.insert(dto);
		
	}

	@Override
	public void updateBook(BookDto dto) {
		int rowCount = dao.update(dto);
		if(rowCount == 0) {
			
		}
		
	}

	@Override
	public void deleteBook(int num) {
		int rowCount = dao.deleteByNum(num);
		if(rowCount == 0) {
			
		}
		
	}

}
