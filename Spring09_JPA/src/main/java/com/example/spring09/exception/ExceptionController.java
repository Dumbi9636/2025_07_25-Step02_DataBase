package com.example.spring09.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden // swagger ui 에서 무시하도록 @Hidden
// 전역 예외 처리기 (컨트롤러에서 발생한 예외를 가로채서 여기서 공통으로 처리가 가능하다)
@RestControllerAdvice 
public class ExceptionController {
	
	// @Valid 어노테이션을 이용해서 검증을 하다가 검증을 통과하지 못하면 여기가 실행된다
	// @ExceptionHandler 예외 타입과 메서드를 연결하는 어노테이션 ("이 메서드는 어떤 예외를 처리하기 위한 메서드다" 라고 스프링에게 알려주기 위함)  
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		// dto 에 필드 검증에 작성한 메시지 정보가 에러 정보에 담겨서 응답 되도록 한다 
		// ex 안에 어떤 필드가 검증에 실패했는지 정보가 들어있다
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap( // FieldError 객체들을 순회하면서 필드명 → 에러 메시지 형태의 Map 으로 변환
                        FieldError::getField, // :: 는 메서드 레퍼런스(method reference) 즉, “이 메서드 그대로 가져다 써라”는 뜻
                        // FieldError::getField => FieldError 객체에서 필드명(key) 을 꺼내라.
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        // FieldError 객체에서 에러 메시지(value) 를 꺼내라.
                        (a, b) -> a, 
                        // 만약 하나의 필드에 여러 개의 에러 메시지가 생기면, 첫 번째 것만 사용하고 나머지는 버려라.
                        /*
                         	(매개변수) -> { 실행문 } 형태

							여기서 (a, b) -> a 는
							두 개의 값이 들어왔을 때 (a, b)
							첫 번째 값 a만 선택해서 반환한다는 의미
							Collectors.toMap() 은 key가 중복되면 충돌이 발생한다
							예를 들어 같은 필드에 여러 검증 에러가 걸렸을 때, 어떤 메시지를 취할지 결정해줘야 함.
							(a, b) -> a 는 "첫 번째 것만 써라"라는 정책
                         */
                        LinkedHashMap::new //최종적으로 결과를 LinkedHashMap 형태로 만들어라. 순서 유지하는 Map으로 결과 반환
                ));
        // 최종 응답 JSON 만들기 
        // code : 에러 코드 문자열
        // errors : 위에서 만든 필드별 에러 메시지 맵
        Map<String, Object> body = Map.of(
                "code", "VALIDATION_ERROR",
                "errors", errors
        );
        // HTTP 상태코드 400 (Bad Request) Error 객체  응답
        // 응답 body 에 위에서 만든 JSON 담아줌
        return ResponseEntity.badRequest().body(body);
        
        /*  이 메소드의 흐름

         	클라이언트가 잘못된 데이터를 보냄 (name 빈값 등)

			@Valid 가 체크 → 실패 → MethodArgumentNotValidException 발생
			
			Spring 이 ExceptionController.handleValidation() 실행
			
			실패한 필드/메시지를 Map 으로 정리
			
			JSON 응답 { code, errors } 형태로 반환
			
			-> !!! 이 정보가 axios 의 catch 로 진행 흐름으로 이동함 
         
         */
    }
}
