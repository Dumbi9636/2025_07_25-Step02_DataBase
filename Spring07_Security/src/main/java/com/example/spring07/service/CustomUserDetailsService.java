package com.example.spring07.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring07.dto.UserDto;

/*
 *	Spring Security 가 로그인 처리시 호출하는 메소드를 가지고 있는 서비스 클래스 정의하기  
 */

@Service // bean 으로 만들기 위한 어노테이션
public class CustomUserDetailsService implements UserDetailsService{
	
	/*
	 *	1. 클라이언트가 로그인을 시도하면 이 메소드를 spring security 가 호출한다
	 *	2. 메소드를 호출하면서 클라이언트가 입력한 userName 을 전달해준다
	 *	3. 우리는 전달된 userName 을 이용해서 DB 에서 해당 유저의 정보를 select 해야한다. 
	 *	4. 만일 select 된 유저 정보가 없으면 예외를 발생시키면 되고 
	 *	5. select 된 정보가 있다면 UserDetails type 객체에 select 된 정보(비밀번호, 권한 등) 를
	 *	   담아서 리턴한다. 
	 *	6. 이 메소드에서 리턴한 UserDetails 객체를 이용해서 spring security 가 로그인 처리를 대신해준다.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//원래는 DB 에서 dao 를 이용해 username 에 해당하는 사용자정보(UserDto) 를 얻어와야 한다.
		//지금은 sample 데이터를 만들어서 사용하기
				
		// 실제 DB 에는 ROLE_XXX 형식으로 저장이 되어 있어야한다 
		String role="";
		if(username.equals("kimgura")) {
			role="ROLE_USER";
		}else if(username.equals("batman")) {
			role="ROLE_STAFF";
		}else if(username.equals("superman")){
			role="ROLE_ADMIN";
		}
				
		//비밀번호를 암호화 하기 위한 객체 
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		//DB 에는 암호화된 비밀번호가 저장되어 있다고 가정하자 (암호화 하기 이전의 비밀번호가 1234 라고 가정)
		String encodedPwd=encoder.encode("1234");
		
		//Sample UserDto 객체 만들기(원래는 DB 에서 읽어온 데이터)
		UserDto dto=UserDto.builder()
							.userName(username)
							.password(encodedPwd)
							.email("aaa@naver.com")
							.role(role)
							.build();
				
		//권한 목록을 List 에 담아서  (지금은 1개 이지만)
		List<GrantedAuthority> authList=new ArrayList<>();
		authList.add(new SimpleGrantedAuthority(dto.getRole()));
				
		//UserDetails 객체를 생성해서 userName과 password, 권한목록을 담고
		UserDetails ud=new User(dto.getUserName(), dto.getPassword(), authList);
		//리턴해준다.
		return ud;
	}

}
