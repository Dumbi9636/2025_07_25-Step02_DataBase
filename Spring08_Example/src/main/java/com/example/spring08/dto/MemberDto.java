package com.example.spring08.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// MyBatis 에서 제공하는 @Alias 어노테이션을 이용해서 MemberDto type 의 별칭을 부여할 수 있다.
// 부여된 별칭을 Mapper xml 문서에서 parameterType or resultType 으로 활용할 수 있다. 
@Alias("memberDto")
@Data // setter, getter 메소드 + toString() 메소드를 오버라이딩해서 필드에 들어있는 정보를 확인할 수 있다. 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	private Integer num;
	private String name;
	private String addr;

}
