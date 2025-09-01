package com.example.spring08.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring08.dto.CommentDto;
import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;
import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;
import com.example.spring08.repository.CommentDao;
import com.example.spring08.repository.GalleryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 의존객체 생성자 자동 생성, 필요한 객체를 bean 에서 찾아서 넣어줌 
public class GalleryServiceImpl implements GalleryService{
	// 의존객체 생성자로 주입 final 중요
	private final GalleryMapper galleryMapper;
	private final CommentDao commentDao;
	
	
	// 업로드된 이미지를 저장할 위치 얻어내기 resources/custom.properties
	@Value("${file.location}")
	private String fileLocation;
		
	
	// 갤러리 목록
	@Override
	public List<GalleryDto> getGalleryList() {
		
		return galleryMapper.getListWithImages();
	}
	
	
	
	
	/*	이 서비스에서 일어나는 DB 관련 작업을 하나의 transaction 단위로 묶기
	 *  
	 *  - @Transactional 어노테이션의 동작 
	 *  
	 *  	1. 작업중에 DataAccessException type 의 예외가 발생하면 자동 rollback 이 된다. 
	 *  
	 *  	2. @Repository 어노테이션이 붙은 dao 에서 DB 관련 작업중에 SQLException 이 발생하면
	 *  		spring 이 해당 예외를 잡아서 DataAccessException 을 자동으로 발생시킨다. 
	 *  		(transaction 에 영향을 주기 위해서) 
	 *  
	 *  	3. @Mapper 를 이용해서 dao 를 만들면 해당 dao 에 자동으로 @Repository 어노테이션이 붙는다. 
	 *  
	 *  	4. 서비스에서 어떤 동작을 하다가 에러가 난 경우 transaction 에 영향을 주고 싶으면
	 *  		일단 예외를 발생시키지 말고 DataAccessException 을 throw 하면 transaction 관리가 된다.
	 *  
	 *  	5. 커스텀 Exception 을 발생시켜서 transaction 을 관리하고 싶으면 커스텀 Exception 클래스를
	 *  		만들때 반드시 RuntimeException 클래스말고 DataAccessException 클래스를 상속받아서 만들고
	 *  		특정 조건 하에서 해당 Exception 을 발생시키면 자동으로 transaction 관리가 된다. 
	 */
	
	@Transactional 	
	// 갤러리 글을 저장하는 메소드 구현체(이미지 + 글)
	@Override
	public void createGallery(GalleryUploadRequest galleryRequest) {
		// 이 Gallery 의 pk 를 미리 얻어낸다 (이미지 정보를 DB 에 저장할때 galleryNum 으로 사용된다) 
		int num = galleryMapper.getSequence();
		
		// 로그인된 userName 
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();		
		//	Gallery 정보도 DB 에 저장한다
		GalleryDto dto = GalleryDto.builder()
				.num(num)
				.title(userName)
				.content(userName)
				.writer(userName)
				.build();
		galleryMapper.insert(dto);
		
		// 업로드된 이미지 파일의 정보를 가지고 있는 배열
		MultipartFile[] images = galleryRequest.getImages();
		
		// 반복문 돌면서 배열에 저장된 MultipartFile 객체를 순서대로 참조하면서 이미지 관련 처리를 한다 
		for(int i=0; i<images.length; i++) {
			// 배열에서 원하는 인덱스에 해당하는 MultipartFile 객체를 참조한다
			MultipartFile image=images[i];	
			//원본 파일명 
			String orgFileName = image.getOriginalFilename();
			//이미지의 확장자를 유지하기 위해 뒤에 원본 파일명을 추가한다 
			String saveFileName=UUID.randomUUID().toString()+orgFileName;	
			String filePath=fileLocation + File.separator + saveFileName;
			try {
				//업로드된 파일을 저장할 파일 객체 생성
				File saveFile=new File(filePath);
				image.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
			// GalleryImageDto 객체에 이미지 하나의 정보를 담고 
			GalleryImageDto imageDto = GalleryImageDto.builder()
					.galleryNum(num)
					.saveFileName(saveFileName)
					.build();
				
			// DB 에 저장한다 
			galleryMapper.insertImage(imageDto);
		}
		
	}
	
	
	// 갤러리 상세보기 메소드 구현체 
	@Override // controller 에 의존하는 utility 같은 메소드 
	public GalleryViewResponse getGallery(int num) {
		// 로그인된 사용자 정보 얻어내기 
		//String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// 로그인 여부 : 로그인을 했으면 true, 로그인을 안했으면(anonymousUser 이면) false 
		//boolean isLogin = userName.equals("anonymousUser") ? false : true; 
		// GalleryDto 얻어내기
		GalleryDto dto = galleryMapper.getData(num);
		//dto 의 content 에서 개행기호를 <br> 요소로 변경한다음 다시 넣기
		String result = dto.getContent().replace("r\n", "<br>").replace("\n", "<br>");
		dto.setContent(result);
		// 이미지 목록
		List<GalleryImageDto> images = galleryMapper.getImageList(num);
		// 댓글 목록 (commentDao 주입 받아서 사용)
		List<CommentDto> commentList = commentDao.selectList(num);
		
		// .build () 하는 시점에 GalleryViewResponse 객체에 담긴다 (setter 가 아닌 @build 이용이기에) 
		return GalleryViewResponse.builder()
				.commentList(commentList)
				.dto(dto)
				.images(images)
				.build();
	}
	

	
}
