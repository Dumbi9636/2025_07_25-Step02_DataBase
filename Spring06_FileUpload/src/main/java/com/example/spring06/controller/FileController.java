package com.example.spring06.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring06.dto.FileDto;

@Controller
public class FileController {
	/*  custom.properties 파일이 로딩 되기 위해서는 main 메소드가 
	 *  있는 클래스에 @PropertiesSource 어노테이션 설정이 되어 있어야 한다.
	 * 
	 *  custom.properties 파일에 있는 파일 업로드
	 *  경로를 읽어와서 필드에 담는다
	 */
	
	
	
	//파일을 저장할 위치
	@Value("${file.location}")
	private String fileLocation; 
	
	
	
	// 파일 다운로드를 해주는 컨트롤러 메소드의 리턴 type   
	// => ResponseEntity<InputStreamResource> 
	@GetMapping("/file/download")
	public ResponseEntity<InputStreamResource> download(String orgFileName, String saveFileName, long fileSize) {
		/*  파일을 다운로드 해주기 위해서는 원본파일명, 저장된파일명, 파일의 실제 크기가 필요하다
		 *  원래는 다운로드 해줄 파일의 PK 를 이용해서 DB 에서 읽어와야 하지만 
		 *  이 예제에서는 GET 방식 파라미터로 넘어오는 값을 활용한다.
		 */
		
		//원래는 DB 에서 읽어와야 하지만 지금은 다운로드해줄 파일의 정보가 요청 파라미터로 전달된다.
		try {
			//다운로드 시켜줄 원본 파일명
			String encodedName=URLEncoder.encode(orgFileName, "utf-8");
			//파일명에 공백이 있는경우 파일명이 이상해지는걸 방지
			encodedName=encodedName.replaceAll("\\+"," ");
			//응답 헤더정보(스프링 프레임워크에서 제공해주는 클래스) 구성하기 (웹브라우저에 알릴정보)
			HttpHeaders headers=new HttpHeaders();
			//파일을 다운로드 시켜 주겠다는 정보
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); 
			//파일의 이름 정보(웹브라우저가 해당정보를 이용해서 파일을 만들어 준다)
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+encodedName);
			//파일의 크기 정보도 담아준다.
			headers.setContentLength(fileSize);			
			//읽어들일 파일의 경로 구성
			String filePath=fileLocation + File.separator + saveFileName;
			
			//파일에서 읽어들일 스트림 객체
			InputStream is=new FileInputStream(filePath);
			//InputStreamResource 객체의 참조값 얻어내기
			InputStreamResource isr=new InputStreamResource(is);
			//ResponseEntity 객체를 구성해서 
			ResponseEntity<InputStreamResource> resEntity=ResponseEntity.ok()
					.headers(headers)
					.body(isr);
			//리턴해주면 파일이 다운로드 된다.
			return resEntity;
		}catch(Exception e) {
			//예외 정보를 콘솔에 출력
			e.printStackTrace();
			//예외 발생시키기 
			throw new RuntimeException("파일을 다운로드 하는중에 에러 발생!");
		}		
	}
	
	
	
	// 파일 업로드 2 
	@PostMapping("/file/upload2")
	public String fileUpload2(Model model, FileDto dto) { // 3개보단, Dto 의 필드에 담아 쓰기
		
		// 매개변수에 전달된 FileDto 객체에 폼 전송된 내용이 모두 들어있다.
		String title = dto.getTitle();
		MultipartFile myFile = dto.getMyFile();
		
		
		//원본 파일명 
		String orgFileName = myFile.getOriginalFilename();
		//파일의 크기
		long fileSize = myFile.getSize();
		//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
		String saveFileName = UUID.randomUUID().toString() + orgFileName;
		//저장할 파일의 전체 경로 구성하기
		String filePath=fileLocation + File.separator + saveFileName;
		try {
			//업로드된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath);
			//원하는 곳으로 파일을 이동 시킨다 (원하는 곳에 파일을 저장한다)
			myFile.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		//원래는 DB 에 저장해야 하지만 테스트를 위해 view page 에 전달해서 출력한다. 
		model.addAttribute("title", title);
		model.addAttribute("orgFileName", orgFileName);
		model.addAttribute("fileSize", fileSize);
		model.addAttribute("saveFileName", saveFileName);
		
		return "file/upload";
	}
	
	
	
	// 파일업로드 1
	@PostMapping("/file/upload")
	public String fileUpload(String title, MultipartFile myFile, Model model) {
		
		//원본 파일명 
		String orgFileName = myFile.getOriginalFilename();
		//파일의 크기
		long fileSize = myFile.getSize();
		//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
		String saveFileName = UUID.randomUUID().toString() + orgFileName;
		//저장할 파일의 전체 경로 구성하기
		String filePath=fileLocation + File.separator + saveFileName;
		try {
			//업로드된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath);
			//원하는 곳으로 파일을 이동 시킨다 (원하는 곳에 파일을 저장한다)
			myFile.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		//원래는 DB 에 저장해야 하지만 테스트를 위해 view page 에 전달해서 출력한다. 
		model.addAttribute("title", title);
		model.addAttribute("orgFileName", orgFileName);
		model.addAttribute("fileSize", fileSize);
		model.addAttribute("saveFileName", saveFileName);
		
		return "file/upload";
	}
	
	
	
	@GetMapping("/file/new")
	public String fileNew() {
		
		return "file/new";
	}
}