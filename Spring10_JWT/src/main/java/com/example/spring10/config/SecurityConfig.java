package com.example.spring10.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.spring10.filter.JwtFilter;

@Configuration //설정 클래스라고 알려준다
@EnableWebSecurity //Security 를 설정하기 위한 어노테이션
@EnableMethodSecurity(securedEnabled = true) //Controller 메소드에서 권한 체크 가능 하도록 
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	/*
	 *  매개변수에 전달되는 HttpSecurity 객체를 이용해서 우리의 프로젝트 상황에 맞는 설정을 기반으로 
	 *  만들어진 SecurityFilterChain 객체를 리턴해주어야 한다.
	 *  또한 SecurityFilterChain 객체도 스프링이 관리하는 Bean 이 되어야 한다  
	 */
	@Bean //메소드에서 리턴되는 SecurityFilterChain 을 bean 으로 만들어준다.
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		// Filter 검증을 받지 않는 화이트리스트
		String[] whiteList= {"/swagger-ui/**", "/v3/api-docs/**", "/v1/notice", "/upload/**"}; 
		 
		httpSecurity
		/*
		   <iframe 은 사용하지 않아서 주석처리함>
		  .headers(header->
			//동일한 origin 에서 iframe 을 사용할수 있도록 설정(default 값은 사용불가)
			header.frameOptions(option->option.sameOrigin()) //SmartEditor 에서 필요함
			)
		 */
		.csrf(csrf->csrf.disable())
		// 람다식 표현(1줄코딩) 
		.authorizeHttpRequests(config ->
		// 리턴값
			config
				// 화이트리스트에 담는것 뿐만 아니라, 특정 메소드의 특정 경로만 허용하게 할 수 있다. 
				.requestMatchers(whiteList).permitAll() // 화이트 리스트에 있는 것은 모두 통과 
				.requestMatchers("/admin/**").hasRole("ADMIN") // admin 으로 시작하는 요청은 "ADMIN" 이라는 Role이 있어야한다 
				.requestMatchers("/staff/**").hasAnyRole("ADMIN", "STAFF") // staff 로 시작하는 요청은 admin 이나 staff 를 가지고 있어야한다 
				.requestMatchers(HttpMethod.POST, "/v1/user", "/v1/login").permitAll() //api 회원가입 요청은 받아들이도록 메소드 별로 세분화가능(여기서는 post 요청,인 경우 v1/user, v1/login)이 맞을때 토오가 
				.requestMatchers(HttpMethod.GET, "/v1/board", "/v1/board/**").permitAll()	
				.anyRequest().authenticated()
		)
		// 세션을 사용하지 않는다는것은 서버가 클라이언트를 기억하지 않겠단 의미 
		.sessionManagement(config ->
			//세션을 사용하지 않도록 설정한다.
			config.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sessionCreationPolicy 를 StateLess : 사용하지않겠다는 의미 (세션을 사용 x)
		)
		// jwt filter 를 거치고 나서 spring security 가 수행되야 한다. 
		//JwtFilter 를 Spring Security 필터보다 미리 수행되게 하기 
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		// 설정 정보를 가지고 있는 HttpSecurity 객체의 build() 메소드를 호출해서 리턴되는 객체를 리턴해준다.
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
			BCryptPasswordEncoder encoder, UserDetailsService service) throws Exception{ // AuthenticationManager 에는 userDetailsService 가 들어간다 
		//적절한 설정을한 인증 메니저 객체를 리턴해주면 bean 이 되어서 Spring Security 가 사용한다 
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(service)
				.passwordEncoder(encoder)
				.and()
				.build();
	}
	
}