package com.example.spring09.service;

import java.util.List;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;

public interface EmployService {
	// 사원 목록
	public List<EmpDto> getEmpList();
	// 부서 목록
	public List<DeptDto> getDeptList();
	// 부서 정보가 포함되어있는 사원 정보
	public EmpDeptDto getEmpDetail(int empno);
	// 부서 정보
	public DeptDto getDeptDetail(int deptno);
	
}
