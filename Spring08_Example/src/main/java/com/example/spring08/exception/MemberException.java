package com.example.spring08.exception;
/*
 *	1. RuntimeExcpetion 클래스를 상속받은 나만의 예외 클래스를 정의한다 
 *	
 *	2. Member 카테고리 관련 작업을 하다가 발생하는 에러를 처리할 예정이다 
 *
 */
public class MemberException extends RuntimeException {
	
	// 열거형 클래스
	public enum Reason{
		NOT_FOUND, UPDATE_FAILED, DELETE_FAILED
		
	}
	
	// 필드
	// final 은 상수값으로 한번 값이 결정되면 수정이 불가능함 
	public final Reason reason;
	

	// 생성자
	public MemberException(Reason reason, String message) {
		// 부모 생성자에 예외 메세지를 전달한다. 
		super(message);
		// 필드에 예외의 원인을 저장한다
		this.reason = reason;
	}
	// 예외 객체를 편리하게 만들어서 리턴해주는 편의 메소드 
	public static MemberException notFound(int num) {
		return new MemberException(Reason.NOT_FOUND, num+"번 회원이 없습니다");
	}
	public static MemberException updateFaild(int num) {
		return new MemberException(Reason.UPDATE_FAILED, num+"번 회원 수정실패");
	}
	public static MemberException deleteFailed(int num) {
		return new MemberException(Reason.DELETE_FAILED, num+"번 회원이 없습니다");
	}
	
	
}
