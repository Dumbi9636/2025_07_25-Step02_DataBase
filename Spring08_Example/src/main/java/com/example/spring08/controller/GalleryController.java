package com.example.spring08.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;
import com.example.spring08.service.GalleryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GalleryController {
	
	// 의존객체 주입 controller 는 service 에 의존한다 service를 구현한 serviceimpl 은 mapper(DAO)에 의존한다  
	private final GalleryService galleryService;
	
	
	// 갤러리 목록 
	@GetMapping("/gallery/list")
	public String gallerylist(Model model) {
	
		// model 객체에 담기
		model.addAttribute("list", galleryService.getGalleryList());
		
		return "gallery/list";
	}
	
	
	// 새 글 작성 폼
	@GetMapping("/gallery/new-form")
	public String newForm() {
		
		return "gallery/new-form";
	}
	
	
	// 새 글 작성 요청
	@PostMapping("gallery/save")
	public String gallerySave(GalleryUploadRequest uploadRequest, RedirectAttributes ra) {
		// 업로드된 파일의 모든 정보를 GalleryUploadRequest 의 images 라는 MultipartFile[] 객체에 담아서 
		// 전달해준다. 
		galleryService.createGallery(uploadRequest);
		// 리다일렉트 된 페이지에 전달할 메세지 
		ra.addAttribute("message", "Gallery 정보를 성공적으로 저장했습니다");
		
		return "redirect:/gallery/save";
	}
	
	
	// 글 상세보기
	@GetMapping("/gallery/view")
	public String galleryView(int num, Model model) {
		// userName, isLogin, GalleryDto, images, commentList 가 들어있는 galleryViewResponse(DTO) 에 담긴 갤러리 번호를 가져와서
		GalleryViewResponse response = galleryService.getGallery(num);
		// model 객체에 담고
		model.addAttribute("res", response);
		// 타임리프 페이지에서 응답하기 (res 키값 사용하면 됨)
		return "gallery/view";
	}
	
	
	
	
	
	
}
