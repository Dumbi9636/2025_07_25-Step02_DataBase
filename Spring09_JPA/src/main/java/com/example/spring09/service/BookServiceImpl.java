package com.example.spring09.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.spring09.entity.Book;
import com.example.spring09.dto.BookDto;
import com.example.spring09.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	// JPA 의존객체 주입
	private final BookRepository bookRepo;
	
	@Transactional(readOnly=true)
	@Override
	public List<BookDto> getAll() {
		List<BookDto> dtoList = bookRepo.findAllNativeQuery()
				.stream().map(BookDto :: toDto).toList();
		return dtoList;
	}
	
	
	
	@Transactional(readOnly=true)
	@Override
	public BookDto getBook(long id) {
		Book entity = bookRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 도서는 존재하지 않습니다"));
				
		return BookDto.toDto(entity);
	}

	
	
	@Transactional
	@Override
	public void addBook(BookDto dto) {
	
		bookRepo.save(Book.toEntity(dto));
	}

	
	
	@Transactional
	@Override
	public void updateBook(BookDto dto) {
		
		Book entity = bookRepo.findById(dto.getId())
				.orElseThrow(()-> new IllegalArgumentException("수정할 도서가 존재하지 않아요"));
		entity.setTitle(dto.getTitle());
		entity.setAuthor(dto.getAuthor());
		entity.setPublisher(dto.getPublisher());
	}

	
	
	@Transactional
	@Override
	public void deleteBook(long id) {
		if(!bookRepo.existsById(id)) {
			throw new IllegalArgumentException("삭제할 도서가 존재하지 않아요");
		}
		bookRepo.deleteById(id);
	}



	
	

}