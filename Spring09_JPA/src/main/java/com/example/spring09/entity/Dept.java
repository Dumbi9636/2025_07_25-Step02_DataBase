package com.example.spring09.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Dept { // 부서 정보를 저장할 Entity
	
	@Id
	private Integer deptno; // 부서 번호가 id 역할을 한다 
	
	private String dname;
	private String loc;
	
}
