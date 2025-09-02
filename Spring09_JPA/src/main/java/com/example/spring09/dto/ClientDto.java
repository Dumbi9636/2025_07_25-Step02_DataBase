package com.example.spring09.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.spring09.entity.Client;

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
	private String userName;
	private LocalDateTime creadtedAt;
	private LocalDateTime updatedAt;
	private LocalDate birthday;
	
	// static toDto() 메소드
	public static ClientDto toDto(Client entity) {
		return ClientDto.builder()
				.num(entity.getNum())
				.userName(entity.getUserName())
				.creadtedAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.birthday(entity.getBirthday())
				.build();
	}
	
	// non static toEntity () 메소드
	public Client toEntity() {
		return Client.builder()	
				.num(num)
				.userName(userName)
				.createdAt(updatedAt)
				.updatedAt(updatedAt)
				.birthday(birthday)
				.build();
	}
}
