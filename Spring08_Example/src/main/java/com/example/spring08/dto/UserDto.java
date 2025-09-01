package com.example.spring08.dto;

import org.springframework.web.multipart.MultipartFile;
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
	
	private String createdAt;
	private String updatedAt;
}
