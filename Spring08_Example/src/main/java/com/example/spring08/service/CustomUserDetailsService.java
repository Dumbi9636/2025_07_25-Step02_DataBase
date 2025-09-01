package com.example.spring08.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring08.dto.UserDto;
import com.example.spring08.repository.UserDao;

import lombok.RequiredArgsConstructor;

/*
 *	Spring Security 가 로그인 처리시 호출하는 메소드를 가지고 있는 서비스 클래스 정의하기  
 */

@Service // bean 으로 만들기 위한 어노테이션
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService{
	
	// UserDao 객체 주입 받기
	private final UserDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// DB 에서 username 에 해당하는 정보가 있는지 select 해본다
		UserDto dto = dao.getByUserName(username);
		
		// 만일 존재하지 않는 사용자라면
		if(dto == null) {
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다");
		}
				
		//권한 목록을 List 에 담아서  (지금은 1개 이지만)
		List<GrantedAuthority> authList=new ArrayList<>();
		authList.add(new SimpleGrantedAuthority(dto.getRole()));
				
		//UserDetails 객체를 생성해서 userName과 password, 권한목록을 담고
		UserDetails ud=new User(dto.getUserName(), dto.getPassword(), authList);
		//리턴해준다.
		return ud;
	}

}
