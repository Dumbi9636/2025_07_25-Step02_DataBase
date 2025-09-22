package com.example.spring10.service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring10.dto.PwdChangeRequest;
import com.example.spring10.dto.UserDto;
import com.example.spring10.exception.PasswordException;
import com.example.spring10.exception.UserNameException;
//import com.example.spring10.exception.PasswordException;
import com.example.spring10.repository.UserDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 알아서 final 필드에 대해 생성자를 만든다. 
						//  -> 스프링 프레임워크가 해당 생성자에 필드값을 넣어준다.
public class UserServiceImpl implements UserService {
	
	private final UserDao dao;
	// 비밀번호 암호화하기 위한 객체도 Spring bean container 로 부터 주입 받는다.  
	private final PasswordEncoder encoder;
	
	// 업로드된 이미지를 저장할 위치 얻어내기
	@Value("${file.location}")
	private String fileLocation;
	
	
	
	
	// 사용자를 추가하는 메소드 
	@Override
	public void createUser(UserDto dto) {
		// 사용자가 입력한 userName 으로 select 되는 값이 있는지 읽어와본다
		// 이 값이 null 이어야 사용가능한 아이디이다. 
		UserDto result = dao.getByUserName(dto.getUserName());
		// null 이 아닌 경우 Exception 을 발생시킨다 
		if(result != null) {
			// custom 예외를 발생시킨다 => ExceptionController 의 UserNameException type 을 처리하는
			// 메소드의 실행의 흐름이 넘어간다 
			throw new UserNameException("이미 사용중인 아이디입니다");
			// 위 excpetion 은 exception controller 에서 처리됨 
		}
		// null 일 경우 아래가 실행된다. 
		// 날것의 비밀번호를 암호화 해서 
		String encodedPwd = encoder.encode(dto.getPassword());
		// dto 에 다시 담는다
		dto.setPassword(encodedPwd);
		// DB 에 저장하기
		dao.insert(dto);
	}
	
	
	// 특정 사용자 이름(userName)으로 DB에서 회원 정보를 찾아서 반환하는 메소드
	@Override
	public UserDto getUser(String userName) {
		
		return dao.getByUserName(userName);
	}

	
	// 비밀번호 수정을 위한 구현 메소드 
	@Override
	public void updatePassword(PwdChangeRequest pcr) {
		// DB 에 저장된 암호화된 비밀번호를 읽어온다.
		UserDto dto = dao.getByUserName(pcr.getUserName());
		String encodedPwd = dto.getPassword();
		
		// 암호화된 비밀번호와 입력한 비밀번호를 비교해서 일치하는지 확인하기
		boolean isValid = BCrypt.checkpw(pcr.getPassword(), encodedPwd);
		
		// 만일 일치하지 않으면 예외 발생시키기
		if(!isValid) {
			// 500 번 에러 응답
			/*
				서버 내부 로직 문제 때문에 발생.
				요청은 맞는데, 서버 코드 실행 중에 예외가 터지거나 DB, 네트워크 등에서 오류가 난 경우.
				500 Internal Server Error: 서버 내부에서 알 수 없는 에러 발생
			 */
			//throw new RuntimeException("기존 비밀번호가 일치하지 않습니다!");
			
			// 400 번 에러 응답 
			/*
				클라이언트 잘못 때문에 서버가 요청을 처리할 수 없을 때 발생.
				요청 자체가 유효하지 않거나 서버가 이해할 수 없는 경우.
				400 Bad Request: 잘못된 요청 (파라미터 오류, JSON 파싱 실패 등)
			 */
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다!");
			
			// 기존 비밀번호가 일치하지 않았을때 throw 할 Exception
			throw new PasswordException("기존 비밀번호가 일치하지 않습니다!");
		} 
		// 일치하면 새 비밀번호를 암호화 해서 pcr 객체에 담은 다음 DB 에 수정 반영한다.
		dto.setPassword(encoder.encode(pcr.getNewPassword()));
		dao.updatePassword(dto);
	}

	
	// 아이디 중복을 확인 메소드
	@Override
	public Map<String, Object> canUseId(String id) {
		// id 를 이용해서 DB 에 해당 아이디로 가입된 정보가 있는지 읽어와본다 (없으면 null) 
		UserDto dto = dao.getByUserName(id);
		// id 가 사용가능한지 여부(dto 가 null 이면 사용가능한 아이디 이다 )
		boolean canUse = dto == null ? true : false; 
		// Map 에 담아서 리턴한다
		return Map.of("canUse", canUse);
	}

	
	// 개인정보 수정을 위한 메소드 
	@Override
	public void updateUser(UserDto dto) {
		//업로드된 이미지가 있는지 읽어와 본다
		MultipartFile image=dto.getProfileFile();
		//만일 업로드된 이미지가 있다면
		if(!image.isEmpty()) {
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
			//UserDto 에 저장된 이미지의 이름을 넣어준다.
			dto.setProfileImage(saveFileName);
		}
		//UserDao 객체를 이용해서 수정 반영하기 ( dto 의 profileImage 는 null 일수도 있다)
		dao.update(dto);
	}

}
