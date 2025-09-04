package com.example.spring09.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.spring09.entity.Client;

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
	
	@NotBlank(message = "이름은 필수 입니다") // 
	@Size(max=20, message="이름은 최대 20글자 까지 가능합니다")
 	private String userName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
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
	@PastOrPresent(message="생일은 미래일 수 없습니다")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
	private LocalDate birthday;
	
	
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
}
