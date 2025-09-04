package com.example.spring09.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Emp { // 사원 정보를 저장할 Entity
	
	@Id
	private Integer empno; // 사원 번호가 id 역할을 한다 
	
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	//private Integer deptno; // Dept entity 의 deptno를 참조하도록 해야한다
	
	// JPA 에서의 join 방법 
	// Entity 안에 또 다른 Entity 가 있으면 편리하지 않을까? ? ?
	/*
	 * 	Emp 객체 하나는 사원 한명의 정보를 가지고 있다
	 * 	Dpt 객체 하나는 부서 하나의 정보를 가지고 있다 
	 * 	Emp 객체 안에 있는 Dept 객체는 Emp 객체가 가지고 있는 해당사원의 부서 정보를 가지게 하고싶다!!
	 * 	
	 * 	name="deptno" 는 Emp 테이블의 칼럼명을 결정한다.
	 * 	referencedColumnName = "deptno" Dept 테이블의 어떤 칼럼을 참조할지 결정한다(생략시 자동으로 @Id 칼럼 참조)
	 */
	
	@ManyToOne // 다 대 일 관계 => emp 테이블의 여러개의 row가 -> dept 테이블의 하나를 참조할 수 있음. 
	@JoinColumn(name = "deptno", referencedColumnName="deptno") // Emp 테이블의 FK 컬럼
	private Dept dept; // Dept dept;라고 쓰면 → Emp가 Dept 타입 객체를 참조하는 것, JPA가 이걸 보고 Emp와 Dept 테이블 간의 연관관계를 매핑
}
