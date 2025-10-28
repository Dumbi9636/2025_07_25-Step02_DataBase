package com.example.spring10.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
	private int num;
	private String writer;
	private String content;
	private String targetWriter;
	private int groupNum;
	private int parentNum;
	private String deleted;
	private String createdAt;
	private String profileImage; // 프로필이미지를 출력하기 위한 필드 
	private int replyCount; // 대댓글의 갯수를 저장하기 위한 필드
	
	// 댓글 페이징 처리를 위한 필드
	private int startRowNum;
	private int endRowNum;
}
