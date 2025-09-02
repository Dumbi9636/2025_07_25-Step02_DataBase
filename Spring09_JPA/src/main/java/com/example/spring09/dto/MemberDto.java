package com.example.spring09.dto;

import com.example.spring09.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
	private int num;
	private String name;
	private String addr;
	
	// Entity 를 매개변수로 전달하면 dto 를 리턴하는 static 메소드 만들어두기
	public static MemberDto toDto(Member entity) {
		return MemberDto.builder()
				.num(entity.getNum())
				.name(entity.getName())
				.addr(entity.getAddr())
				.build();
	}
}
