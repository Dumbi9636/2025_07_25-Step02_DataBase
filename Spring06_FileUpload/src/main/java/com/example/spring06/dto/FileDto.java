package com.example.spring06.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileDto {
	private String title;
	// <input type="file" name="myFile" > 에서 name 속성의 값과 필드명을 일치시켜야한다
	private MultipartFile myFile;
	// 이외의 다른 필드도 있다고 가정 
	
}
