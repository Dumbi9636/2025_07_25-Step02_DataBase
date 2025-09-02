package com.example.spring09.entity;

import com.example.spring09.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// entity 클래스에는 @Data 어노테이션을 붙이면 나중에 문제 발생 setter,getter 로 사용
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="MEMBER_INFO") // 이 클래스가 JPA 가 관리할 엔티티 객체임을 선언 필드명과 name 이 일치한다면 name 속성은 생략이 가능함
public class Member {
	// num 이라는 필드에 대해서 2개의 어노테이션 추가 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // GenerationType.Auto 는 자동으로 num 을 넣어줌(시퀀스 생성, 시퀀스를 만들지 않아도 자동으로 증가되는 숫자값을 넣을수도 있다.)
	private Integer num; // num 은 PK로 설정됨
	
	
	private String name;
	private String addr;
	
}