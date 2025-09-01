package com.example.spring08.test;

import org.springframework.stereotype.Component;

@Component // @Component 어노테이션을 붙여서 spring 이 관리하는 bean 으로 만든다
public class WritingUtil {
	
	// 가상의 편지를 쓰는 메소드
	public void writeLetter() {
	System.out.println("편지를 써요");	
	try {
		Thread.sleep(1000);
	}catch(Exception e) {}
			
	}
	
	// 가상의 보고서를 쓰는 메소드
	public void writeReport() {
	System.out.println("보고서를 써요");
	try {
		Thread.sleep(1000);
	}catch(Exception e) {}
			
	}
	
	
	// 가상의 다이어리를 쓰는 메소드
	public void writeDiary() {
	System.out.println("일기를 써요");	
	try {
		Thread.sleep(1000);
	}catch(Exception e) {}
			
	}	
	
}
