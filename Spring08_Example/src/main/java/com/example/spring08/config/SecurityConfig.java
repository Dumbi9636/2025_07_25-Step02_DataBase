package com.example.spring08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정에 관련된 bean 이라고 알려준다. 
@EnableWebSecurity // Spring Security 를 설정하기 위한 어노테이션
public class SecurityConfig {
	
	/*
	 * 메소드에 @Bean 어노테이션을 붙여 놓으면
	 * 1. spring 이 자동으로 해당 메소드를 호출하고
	 * 2. 매개변수에 필요한 type 객체가 있으면 알아서 spring bean container 에서 해당type 객체를 찾아서 전달한다
	 * 3. 또한 메소드가 리턴해주는 객체를 spring bean container 에서 관리해준다. 
	 */
	@Bean
	public SecurityFilterChain securityFileterChain(HttpSecurity httpSecurity) throws Exception{
		// security 를 통과하는 white list
		String[] whiteList = {"/", "/user/loginform", "/user/login-fail", 
				"/user/expired","/user/signup-form", "/user/signup", 
				"/test/**", "/user/check-id", "/board/list", "/board/view","/upload/**",
				"/gallery/list", "/gallery/view"};
		
		httpSecurity 
		.csrf(csrf->csrf.disable()) // csrf 검증 사용하지 않기 
		.authorizeHttpRequests(config-> // 함수표현 (화살표 다음은 리턴할 값)
			config
			.requestMatchers(whiteList).permitAll() // whiteList 는 무조건 받아들이고
			// 역할에 따라서 요청가능한 경로를 구분한다
			.requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만
			.requestMatchers("/staff/**").hasAnyRole("ADMIN", "STAFF") // 관리자 + 스태프
			.anyRequest().authenticated() // 다른 모든 요청은 인증을 진행한다 
		)
		.formLogin(config -> // (화살표 다음을 리턴할 값)
			config
				// 인증을 거치지 않은 사용자를 리다일렉트 시킬 경로  설정 
				.loginPage("/user/required-loginform") // 로그인 페이지의 위치
				// 로그인 폼의 action
				.loginProcessingUrl("/user/login") // 로그인 프로세스 위치
				// 로그인 폼의 사용자명 입력란의 name 속성의 값 <input type="text" name"userName" >
				.usernameParameter("userName") // username 의 파라미터명 
				// 로그인 폼의 사용자명 입력란의 name 속성의 값 <input type="password" name"password" >
				.passwordParameter("password") // password 의 파라미터명
				
				// 로그인  성공시 foward 될 url (login welcome 응답)
				//.successForwardUrl("/user/login-success") // 로그인 성공했을때 포워드 되는 경로
				
				// 로그인 성공시 동작하는 핸들러 객체 등록하기
				.successHandler(new AuthSuccessHandler())
				
				// 계정, 비밀번호 문제로 로그인 실패시 forward 이동될 url (실패 되었다고 응답)
				.failureForwardUrl("/user/login-fail") // 로그인 실패했을때 포워드 되는 경로
				.permitAll() // 위의 요청들은 whiteList 에 없어도 요청 가능하도록 설정
				
				// -> controller 와 view page 는 우리가 만들어야한다. 
		)
		.logout(config ->
			config
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/")
				.permitAll()
		)
		.exceptionHandling(config -> 
			// 권한 부족시 forward 이동될 url
			config.accessDeniedPage("/user/denied")
		)
		.sessionManagement(config ->
			config
				// 최대 세션 허용 갯수 설정 ( 다중 기기 로그인 허용 여부 )
				.maximumSessions(3)
				// 로그인이 해제 되었을때 응답할 url
				.expiredUrl("/user/expired")
		);
		
		return httpSecurity.build();
	}
	
	
	//비밀번호를 암호화 해주는 객체를 bean 으로 만든다.
	@Bean
	PasswordEncoder passwordEncoder() { 
			
		//여기서 리턴해주는 객체도 bean 으로 된다.
		return new BCryptPasswordEncoder();
	}
		
		
	//인증 메니저 객체를 bean 으로 만든다. (Spring Security 가 자동 로그인 처리할때도 사용되는 객체)
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http,
			BCryptPasswordEncoder encoder, UserDetailsService service) throws Exception{
		//적절한 설정을 한 인증 메니저 객체를 리턴해주면 bean 이 되어서 Spring Security 가 사용한다 
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(service)
				.passwordEncoder(encoder)
				.and()
				.build();
	}
}
