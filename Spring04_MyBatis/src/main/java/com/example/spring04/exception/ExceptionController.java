package com.example.spring04.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 예외 처리를 할 컨트롤러에는 @ControllerAdvice 어노테이션을 붙여준다 
@ControllerAdvice
public class ExceptionController {
	
	/*	Spring 프레임워크가 동작하는 과정중에 DataAccessException type 의 예외가 발생하면 
	 * 	이 메소드가 자동으로 호출된다. 
	 * 	메소드의 매개변수에는 해당 예외 객체의 참조값이 전달된다. 
	 * 	예외 관련 처리를 하고 에러 페이지를 응답할 수 있다. 
	 */
	
	// DataAccessException 예외 발생 시 아래가 실행된다
	// @ExceptionHandler( 처리하고싶은 예외 type ) 
	@ExceptionHandler(DataAccessException.class)
	public String dataAccess(DataAccessException dae, Model model) {
		
		// title 이라는 키값으로 에러 제목 
		model.addAttribute("title", "DB 관련 작업중에 에러가 발생했습니다");
		// message 라는 키값으로 에러 메세지 DataAccessException 객체에는 getMessage 라는 메소드가 있음 
		model.addAttribute("message", dae.getMessage());
		// status 라는 키값으로 상태 정보 
		model.addAttribute("status", 500);
		
		// /WEB-INF/views/error/data-access.jsp 페이지에서 에러 정보를 응답한다.
		return "error/data-access";
	}
	
	@ExceptionHandler(MemberException.class)
	public String memberException(MemberException me, Model model) {
		
		model.addAttribute("title", "Member 관련 작업중에 에러가 발생했습니다");
		model.addAttribute("message", me.getMessage());
		model.addAttribute("reason", me.reason.name());
		
		return "error/member-exception";
	}
}
