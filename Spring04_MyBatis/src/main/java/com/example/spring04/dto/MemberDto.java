package com.example.spring04.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

// MyBatis 에서 제공하는 @Alias 어노테이션을 이용해서 MemberDto type 의 별칭을 부여할 수 있다.
// 부여된 별칭을 Mapper xml 문서에서 parameterType or resultType 으로 활용할 수 있다. 
@Alias("memberDto")
@Setter
@Getter
public class MemberDto {
	private Integer num;
	private String name;
	private String addr;
}
