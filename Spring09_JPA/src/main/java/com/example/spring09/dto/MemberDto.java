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
	// Entity 와 같이 사용하기 위해 int 대신에 Integer 를 사용한다. (long 대신에 Long 을 사용)
	private Integer num; 
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
	
	
	// 객체의 필드에 저장된 값을 이용해서 Entity 객체를 만들어서 반환하는 non static 메소드 
	// 객체 하나하나 당 갖고 있는 메소드
	public Member toEntity() {
		return Member.builder()	
				.num(this.num) // dto 의 num 이라는 필드가 기본 data type 이기 때문에 기본 data type 은 default 가 0 이라서 문제가 됨. 
							  // 따라서 참조 데이터타입인 num 을 넣으면 디폴드 값으로 null 이 들어감. null 일 가능성 있음. 편의를 위해 int-> integer 로 바꿀 예정
				.name(this.name)
				.addr(addr) // 멤버 메소드 안에서  this. 은 생략 가능
				.build();
	}
}
