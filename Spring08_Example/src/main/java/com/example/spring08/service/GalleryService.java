package com.example.spring08.service;

import java.util.List;


import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;

public interface GalleryService {
	public List<GalleryDto> getGalleryList();
	public void createGallery(GalleryUploadRequest galleryRequest); // title, content, images[] 이 들어있는 DTO를 담는다
	public GalleryViewResponse getGallery(int num); // userName, isLogin, GalleryDto, images, commentList 가 들어있는 DTO 
	
}
