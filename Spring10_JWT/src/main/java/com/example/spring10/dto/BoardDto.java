package com.example.spring10.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

//MyBatis XML에서 parameterType, resultType을 간단하게 쓰기 위해 어노테이션 삽입
@Alias("boardDto") 
public class BoardDto {
	private int num;
	private String writer;
	private String title;
	private String content;
	private int viewCount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy년 MM월 dd일 HH:mm")
	private LocalDateTime createdAt;
	
	// 페이징 처리를 위한 필드
	private int startRowNum;
	private int endRowNum;
	
	// 프로필 이미지 출력을 위한 필드 
	private String profileImage;
	
	// 이전글, 다음글 처리를 위한 필드
	private int prevNum;
	private int nextNum;
	
	// 검색 키워드를 담기 위한 필드
	private String keyword;
	
	// 검색 조건을 담기 위한 필드
	private String search;
	
	// default 생성자는 만들지 않아도 있는 것으로 간주됨
	
	
	
}
