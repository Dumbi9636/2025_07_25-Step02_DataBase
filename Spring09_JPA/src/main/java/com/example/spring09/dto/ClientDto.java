package com.example.spring09.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.spring09.entity.Client;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
	private Long num;
	
	@NotBlank(message = "이름은 필수 입니다") 
	@Size(max=20, message="이름은 최대 20글자 까지 가능합니다")
 	private String userName;
	
	/*
	 * RestController 에서 dto 를 리턴하면 해당 dto 의 필드에 있는 내용이 json 문자열로 변경되는데
	 * LocalDataTime 에 있는 날짜 정보를 @JsonFormat 어노테이션을 이용해서 원하는 형식으로 변경되도록 한다 
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm")
	private LocalDateTime createdAt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm")
	private LocalDateTime updatedAt;
	// 위 JsonFormat 은 input type data 에 형식을 정해놨더라도 적용이 안됨.
	
	/*
	 * 	@Past	
	 * 	@PastOrPresent
	 * 	@Future
	 * 	중에 하나로 검증할 수 있다
	 * 
	 * 	input type = "data" 의 value 에 th:value ="${birthday}" 를 출력할때 형식을 맞춰야한다. 
	 * 	사실 ClientDto 의 birthday 라는 필드는 LocalDate type 이기 때문에 
	 * 	출력할때 어떤 형식으로 출력할지를 설정해야 웹브라우저가 해당 날짜를 UI 에 제대로 표시할 수 있다. 
	 * 	그래서 필요한 어노테이션이 @DataTimeFormat 이다. 
	 */
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
	@PastOrPresent(message="생일은 미래일 수 없습니다")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // 표준 날짜 형식 => input type = "date" 에 맞는 형식
	private LocalDate birthday; // LocalDate type 으로 날짜형식을 만들때는 Json 형식으로 만들때 어떤 형식으로 만들지 결정해주는 것이 DateTimeFormat 이다
	// input type = {} value 값에 값을 넣어주면 그 값을 날짜로 
	
	
	// static toDto() 메소드
	public static ClientDto toDto(Client entity) {
		return ClientDto.builder()
				.num(entity.getNum())
				.userName(entity.getUserName())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.birthday(entity.getBirthday())
				.build();
	}
	
	// non static toEntity () 메소드
	public Client toEntity() {
		return Client.builder()	
				.num(num)
				.userName(userName)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.birthday(birthday)
				.build();
	}
	
	// 단순 출력용 생일 문자 메소드
	// formattedBirthday 라는 필드가 있는 것처럼 사용 
	// getFormattedBirthday 메소드를 호출할 수 있다. 
	public String getFormattedBirthday() {
			// 저장된 생일이 있으면 원하는 형식의 날짜 형식을 리턴하고 없으면 null 을 리턴한다
		   String result = 
				   birthday != null ? birthday.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) : null;
		   return result;
	}
}
