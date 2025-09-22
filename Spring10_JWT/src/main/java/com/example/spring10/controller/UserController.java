package com.example.spring10.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring10.dto.PwdChangeRequest;
import com.example.spring10.dto.UserDto;
import com.example.spring10.service.UserService;
import com.example.spring10.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor // 생성자 주입 
public class UserController {
	
	// 의존객체 생성자 주입 방식 활용 (final을 붙인다)
	public final JwtUtil jwtUtil;
	//SecurityConfig 클래스에서 Bean 이된 AuthenticationManager 객체 주입받기 
	public final AuthenticationManager authManager; // AuthenticationManager 는 인증(Authentication)을 총괄하는 핵심 인터페이스
	public final UserService userService;
	
	

	
	// 회원가입 요청처리
	@PostMapping("/user")
	public ResponseEntity<Void> signup(@RequestBody UserDto dto){
		// service 의 createUser 메소드 안에서 처리 
		userService.createUser(dto);
		/*
		 * 	ResponseEntity<Void> 는 204 응답이고 응답의 body 에 아무런 내용이 없는 응답이다
		 * 
		 * 	해당 응답을 하기 위한 code 는 아래와 같다. .noContent().build()
		 */
		return ResponseEntity.noContent().build();
	}
	
	
	/*
	 *  요청 처리후에 특별히 응답할 내용이 없을때 204 응답을 하면 된다. 
	 *  return type => String 에서 ResponseEntity<Void> 로 수정
	 */
	// 비밀번호 부분수정 요청
	@PatchMapping("/user/password")
	public ResponseEntity<Void> passwordUpdate(@RequestBody PwdChangeRequest request) { // PwdChangeRequest 에는 userName, pwd 와  newPwd 가 들어있음
		// 비밀번호를 수정하는데에 userName 도 필드에 있기 때문에 수정반영할때 같이 필요해서 userName 도 얻어와서 전달해줘야한다
		// 클라이언트가 입력한 비밀번호와 수정된 비밀번호는 contextHodler 안에 들어있다
		
		// 로그인된 userName 얻어와서 
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// 요청 파라미터에 userName을 담고
		request.setUserName(userName);
		// userService에 updatePassword 로 수정 요청한다 
		userService.updatePassword(request);
		
		// 특별히 응답할 컨텐츠가 없을 수도 있음(200 응답만 주면 될때) 
		// 이런 경우에는 ResponseEntity<Void> 타입을 리턴해주면 된다. 
		// 빈 content 와 함께 204 응답이 된다. 
		return ResponseEntity.noContent().build();
	}
	
	
	// user 조회 
	@GetMapping("/user")
	public UserDto user() {
		// spring security context 로 부터 로그인된 userName 을 얻어낸다
		String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.getUser(userName);
	}
	
	
	// ping Test 요청 처리 
	@GetMapping("/ping") // 어디선가 /v1/ping 요청을 하면 pong 을 응답 
	// v1/ping 요청은 whitelist 에 없다. 이 요청이 성공하기위해서는 발급받은 key 가 authorization 에 같이 담겨서 요청되어야한다
	// 그냥 요청을 하게되면 403 err(금지된 요청) 토큰을 요청헤더에 authoriziation 이라는 키값을 가져가지 않았기때문 
	public String ping() {
		return "pong";
	}
	
	
	// 로그인 요청 처리
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto dto){
		Authentication authentication=null;
		try {
			UsernamePasswordAuthenticationToken authToken=
					new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());
			//인증 메니저 객체를 이용해서 인증을 진행한다.
			authentication=authManager.authenticate(authToken);
			
		}catch(Exception e) {
			//예외가 발생하면 인증실패(아이디 혹은 비밀번호 틀림 등등...)
			e.printStackTrace();
			// 401 UNAUTHORIZED 에러를 응답하면서 문자열 한줄 보내기 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패!");
		}
		
		//Authentication 객체에는 인증된 사용자 정보가 들어 있다. userName, role 등등 
		//현재는 role 을 하나만 부여하기 때문에 0 번 방에 있는 데이터만 불러오면 된다. 
		GrantedAuthority authority=authentication.getAuthorities().stream().toList().get(0);
		//ROLE_XXX 형식
		String role=authority.getAuthority();
		//"role" 이라는 키값으로 Map 에 담기 (key, value)
		Map<String, Object> claims=Map.of("role", role);

		//예외가 발생하지 않고 여기까지 실행 된다면 인증을 통과 한 것이다. 토큰을 발급해서 응답한다.
		String token=jwtUtil.generateToken(dto.getUserName(), claims); // 프론트에서는 토큰 정보를 확인하려면 디코딩하면된다 
		//발급받은 토큰 문자열을 ResponseEntity 에 담아서 리턴한다.
		return ResponseEntity.ok("Bearer "+token); 
	}
}	