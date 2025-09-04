package com.example.spring09.dto;

import com.example.spring09.entity.Dept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeptDto {
	private Integer deptno;
	private String dname;
	private String loc;
	
	public static DeptDto toDto(Dept d) {
		
		return DeptDto.builder()	
				.deptno(d.getDeptno())
				.dname(d.getDname())
				.loc(d.getLoc())
				.build();
	}
}
