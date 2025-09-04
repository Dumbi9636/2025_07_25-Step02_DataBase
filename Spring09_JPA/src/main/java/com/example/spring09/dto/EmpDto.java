package com.example.spring09.dto;

import java.time.LocalDate;

import com.example.spring09.entity.Emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDto {
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	private Integer deptno;	
	
	public static EmpDto toDto(Emp e) {
		
		return EmpDto.builder()
				.empno(e.getEmpno())
				.ename(e.getEname())
				.job(e.getJob())
				.mgr(e.getMgr())
				.hiredate(e.getHiredate())
				.sal(e.getSal())
				.comm(e.getComm())
				.deptno(e.getDept().getDeptno()) // 부서번호를 넣어주는 부분에 주목!!!!
				// emp entity 에는 deptno 가 없음. 이건 dept entity 에 있기 때문에 dept 후에 deptno 로 참조
				.build();
	}
}
