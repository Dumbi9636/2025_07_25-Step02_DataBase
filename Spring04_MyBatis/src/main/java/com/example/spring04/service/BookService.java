package com.example.spring04.service;

import java.util.List;

import com.example.spring04.dto.BookDto;

public interface BookService {
	public List<BookDto> getAll();
	public BookDto getBook(int num);
	public void addBook(BookDto dto);
	public void updateBook(BookDto dto);
	public void deleteBook(int num);
	
}
