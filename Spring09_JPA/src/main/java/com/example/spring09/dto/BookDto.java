package com.example.spring09.dto;

import com.example.spring09.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
	private long id;
	private String title;
	private String author;
	private String publisher;
	
	// Entity 를 매개변수로 전달하면 dto 를 리턴하는 static 메소드
	public static BookDto toDto(Book entity) {
		return BookDto.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.author(entity.getAuthor())
				.publisher(entity.getPublisher())
				.build();
				
	}
}
