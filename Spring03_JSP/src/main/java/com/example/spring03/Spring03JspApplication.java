package com.example.spring03;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/* 1. application. properties 설정 파일을 읽어들여서 동작을 준비한다
 * 
 * 2. 현재 이 클래스가 존재하는 패키지를 포함해서 모든 하위 패키지를 scan 에서 bean 으로 만들것들을 다 만들고
 * 	  필요하다면 의존성 주입도 실행한다. 
 * 
 * 3. 이 프로젝트는 spring web 프로젝트이기 때문에 내장 tomcat 도 실행해서 서비스를 준비한다.
 */
@SpringBootApplication
public class Spring03JspApplication {
	
	// Run as spring boot app 메뉴를 선택해서 실행하면 여기가 실행된다.
	public static void main(String[] args) {
		SpringApplication.run(Spring03JspApplication.class, args);
	}
	
	// 크롬 브라우저를 자동으로 열어주는 메소드( 기존에는 서버주소를 직접 입력해야했다 )
	@EventListener(ApplicationReadyEvent.class)
	public void openChrome() {
		String url = "http://localhost:9000/";
		// 운영체제의 참조값을 얻어와서 이름을 소문자로 
		String os = System.getProperty("os.name").toLowerCase();
		ProcessBuilder builder = null;
		try {
			if (os.contains("win")) {
				builder = new ProcessBuilder("cmd.exe", "/c", "start chrome " + url);
			} else if (os.contains("mac")) {
				builder = new ProcessBuilder("/usr/bin/open", "-a", "Google Chrome", url);
			} else {
				System.out.println("지원 하지 않는 운영체제 입니다.");
				return;
			}
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
