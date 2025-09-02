package com.example.spring09.entity;

import com.example.spring09.dto.BookDto;
import com.example.spring09.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="BOOK_INFO")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // 시퀀스로 자동 생성
	private Long id;
	
	private String title;
	private String author;
	private String publisher;
	
	
	// dto 를 Entity 로 변경하는 메소드
	public static Book toEntity(BookDto dto) {

		return Book.builder()
			.id(dto.getId() == 0 ? null : dto.getId())
			.title(dto.getTitle())
			.author(dto.getAuthor())
			.publisher(dto.getPublisher())
			.build();
	}
}
