package com.example.spring09.service;

import java.util.List;

import com.example.spring09.dto.BookDto;

public interface BookService {
	public List<BookDto> getAll();
	public BookDto getBook(long id);
	public void addBook(BookDto dto);
	public void updateBook(BookDto dto);
	public void deleteBook(long id);
	
}
