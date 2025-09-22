package com.example.spring10.service;

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

import com.example.spring10.dto.UserDto;
import com.example.spring10.repository.UserDao;

import lombok.RequiredArgsConstructor;

@Service // bean 으로 만들기 위한 어노테이션 
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService { // UserDetailsService 는 spring security 에서 제공해주는 인터페이스 
	
	// userDao 생성자 주입 
	private final UserDao dao;
	
	// userName을 전달받아서 userdetail 을 리턴해주는 객체 (role 정보까지 담겨있다)
	// ex) 김구라를 요청하면 김구라에 대한 자세한 정보를 리턴 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//원래는 DB 에서 dao 를 이용해 username 에 해당하는 사용자정보(UserDto) 를 얻어와야 한다.
		//지금은 sample 데이터를 만들어서 사용하기
		
		// 실제 DB 에 있는 정보를 로딩 
		UserDto dto = dao.getByUserName(username);
		/*
		 * 	Spring Security 에서 role 과 authority 는 약간의 차이가 있지만 편의상 같다라고 생각하자
		 * 
		 *  ROLE_USER 를 예를 들면 
		 *  
		 *  해당 USER 의 role 은 "USER" 
		 *  해당 USER 의 authority 는 "ROLE_USER" 임을 명심하자...  
		 *  
		 */
		
		//권한 목록을 List 에 담아서  (지금은 1개 이지만)
		List<GrantedAuthority> authList=new ArrayList<>();
		// Role 을 포함한 authoryty 객체를 생성해서 authList 에 추가 
		authList.add(new SimpleGrantedAuthority(dto.getRole()));
				
		//UserDetails 객체를 생성해서 
		UserDetails ud=new User(dto.getUserName(), dto.getPassword(), authList);
		//리턴해준다.
		return ud;		
	}

}
