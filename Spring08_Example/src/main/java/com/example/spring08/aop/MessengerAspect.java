package com.example.spring08.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.spring08.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그출력을 도와주는 어노테이션, 이 어노테이션이 붙어있는 클래스의 메소드에서 log 객체를 사용할 수 있다 
@Aspect // 부가기능(횡단 관심사)을 담당하는 코드 모음이라는
@Component
public class MessengerAspect {
	/*
	 *  1. 메소드의 return type 은 String 이고
	 *  2. com.example.spring08.aop 패키지에 속해있는 모든클래스(*) 중에서
	 *  3. get 으로 시작하는 메소드
	 *  4. 메소드의 매개변수는 비어있는 메소드 get*(비어있음)
	 *  
	 *  위의 4가지 조건이 모두 만족하면 아래의 aspect 가 적용된다.
	 */
	@Around("execution(String com.example.spring08.aop.*.get*())")
	public Object checkReturn(ProceedingJoinPoint joinPoint) throws Throwable {
		// 실행 이전 영역 @Before
		
		// aspect 가 적용된 메소드를 실행하고 해당 메소드가 리턴하는 값을 변수에 담기 
		Object obj = joinPoint.proceed();
		// 실행 이후 영역(proceed 이후) @Ater
		
		// 원래 type 으로 casting 
		String returnValue=(String)obj;
		log.debug("원래 리턴한 값:" + returnValue);
		// 리턴값이 있는 메소드에 aspect 를 적용하면 반드시 해당 데이터를 리턴해야한다 	
		// return obj; 원래는 이건데 
		return "뭔 공부야? 놀자 놀자~"; // 다른값을 리턴해줄 수도 있다.
	}
	
	
	// .. 은 매개변수의 모양을 상관하지 않겠다. (갯수와 type을 제한하지 않음)
	//spring 이 관리하는 bean 의 메소드 중에서 리턴 type 이 void 이고 send 로 시작하는 모든 메소드에 적용 
	@Around("execution(void send*(..))") // @Before+@After  개념 => 실행이전이후 전위에서 가능
	public void checkGreeting(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// 메소드에 전달된 인자들 목록을 얻어내기 (매개변수 목록)
		Object[] args = joinPoint.getArgs();
		// 반복문 돌며서 매개변수에 담긴 값들을 하나하나 참조하면서
		for(Object tmp : args) {
			// 찾고 싶은 type 을 확인한다
			if(tmp instanceof String) {// 만일 String type 이라면
				// 찾았다면 원래 type 으로 casting 한다
				String msg=(String)tmp;
				System.out.println("매개변수에 전달된 값:"+msg);
				// log 객체를 이용해서 메세지를 출력해보자
				//log.info("매개변수에 전달된 값:" + msg);
				//log.warn("매개변수에 전달된 값:" + msg);
				//log.error("매개변수에 전달된 값:" + msg);
				log.debug("매개변수에 전달된 값:" + msg); // 디버깅용도 
				
				if(msg.contains("똥깨")) {
					log.error("똥깨는 금지된 단어입니다. 메소드 호출을 차단합니다");
					return; // 여기서 리턴하면 아래의 joinPoint.proceed(); 가 호출이 안된다
				}				
			}	
			if(tmp instanceof MemberDto) { // 만일 MemberDto type 이라면 
				log.debug("매개변수에 전달된 값:" +tmp);
				MemberDto dto = (MemberDto)tmp;
				dto.setName("개구라");
			}
		}
		// 이 메소드를 호출하는 시점에 실제로 aspect 가 적용된 메소드가 수행된다.(호출하지 않으면 수행안됨)
		joinPoint.proceed();
	}
}
