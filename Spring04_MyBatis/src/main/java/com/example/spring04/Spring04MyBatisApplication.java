package com.example.spring04;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@MapperScan(basePackages = "com.example.spring04.mapper")
@SpringBootApplication(scanBasePackages = "com.example.spring04")
public class Spring04MyBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring04MyBatisApplication.class, args);
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
