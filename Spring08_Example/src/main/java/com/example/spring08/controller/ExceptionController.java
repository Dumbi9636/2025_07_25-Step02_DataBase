package com.example.spring08.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring08.exception.PasswordException;

// 예외 컨트롤러는 @ControllerAdvice 어노테이션을 붙여서 bean 으로 만든다
@ControllerAdvice
public class ExceptionController {
	/*
	 * ExceptionController 또는 다른 일반 Controller 에서 리다일렉트 이동하면서
	 * 일회성 데이터를 전달하고 싶을때는 컨트롤러의 매개변수에 RedirectAttributes 를 선언하고 
	 * 해당 객체에 데이터를 담으면 리다일렉트 이동된 view page 에서 해당 데이터를 추출해서 
	 * 사용할 수 있다. 
	 */
	@ExceptionHandler(PasswordException.class)
	public String password(PasswordException pe, RedirectAttributes ra) {
		/*
		 * "exception" 이라는 키값으로 PasswordException 객체를 담는다.
		 * 해당 정보는 리다일렉트 이동된 view page 에서 사용할 Model 객체에 자동으로 담긴다.
		 */
		ra.addFlashAttribute("exception", pe);
		
		
		return "redirect:/user/edit-password";
	}
	/*
	 * "exception" 이라는 키값으로 저장된 객체가 있으면, 비밀번호 수정 정보를 입력하고 전송했지만 비밀번호가 일치하지 않는 경우인 것이고
	 * 저장된 객체가 없다면 그저 비밀번호 수정 링크를 누른 경우이다. 
	 */
}
