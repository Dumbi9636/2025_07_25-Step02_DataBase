package com.example.spring06.controller;
import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring06.dto.GalleryDto;

@Controller
public class GalleryController {
	
	//파일을 저장할 위치 
	@Value("${file.location}")
	private String fileLocation;
	
	
	
	@PostMapping("/gallery/upload")
	public String upload(GalleryDto dto, Model model) {
		// @Data 가 오버라이드한 toString() 메소드를 확인하기 위해
		System.out.println(dto); //원래는 dto 객체의 hash 값이 출력되야 하지만 필드를 확인할수 있는 문자열이 출력된다.
		
		//MultipartFile 객체
		MultipartFile image=dto.getImage();
		//만일 파일이 업로드 되지 않았다면
		if(image.isEmpty()) {
			throw new RuntimeException("이미지가 업로드 되지 않았습니다.");
		}
		//원본 파일명 
		String orgFileName = image.getOriginalFilename();
		//이미지의 확장자를 유지하기 위해 뒤에 원본 파일명을 추가한다 
		String saveFileName=UUID.randomUUID().toString()+orgFileName;
		//저장할 파일의 전체 경로 구성하기
		String filePath=fileLocation + File.separator + saveFileName;
		try {
			//업로드된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath);
			image.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//원래는 DB 에 저장해야 하지만 테스트를 위해 Model 에 담아서 응답한다.
		model.addAttribute("caption", dto.getCaption());
		model.addAttribute("saveFileName", saveFileName);
				
		return "gallery/upload";
	}
	
	
	
	@GetMapping("/gallery/new")
	public String galleryNew() {
		
		return "gallery/new";
	}
}