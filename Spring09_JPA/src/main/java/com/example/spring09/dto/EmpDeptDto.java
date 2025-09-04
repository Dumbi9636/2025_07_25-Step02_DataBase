package com.example.spring09.dto;

import java.time.LocalDate;

import com.example.spring09.entity.Emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmpDeptDto {
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	
	//Emp 와 Dept 에 같이 있는 정보 
	private Integer deptno;
	
	//Dept Entity 에만 있는 정보
	private String dname;
	private String loc;
	
	public static EmpDeptDto toDto(Emp emp) {
		
		return EmpDeptDto.builder()
				.empno(emp.getEmpno())
				.ename(emp.getEname())
				.job(emp.getJob())
				.mgr(emp.getMgr())
				.hiredate(emp.getHiredate())
				.sal(emp.getSal())
				.comm(emp.getComm())
				.deptno(emp.getDept().getDeptno())
				.dname(emp.getDept().getDname())
				.loc(emp.getDept().getLoc())
				.build();
	}
}
