package com.example.spring10.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder 
@AllArgsConstructor // 모든 필드값을 전달 받는 생성자(Builder 를 사용하려면 필요함)
@NoArgsConstructor // 빈생성자
@Data //setter, getter
public class UserDto {
	
	private long num;
	private String userName;
	private String password;
	private String email;
	private String role;
	private String profileImage;
	
	// <input type = “file” name =”profileFile” > 을 처리하기 위한 필드
	private MultipartFile profileFile;
	
	// 날짜 Type 으로 바꿔준다 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm")
	private LocalDateTime updatedAt;
}
