package com.example.spring06.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class GalleryDto {
	private String caption;
	private MultipartFile image;
}
