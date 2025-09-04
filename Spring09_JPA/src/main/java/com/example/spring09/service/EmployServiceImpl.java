package com.example.spring09.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;
import com.example.spring09.entity.Dept;
import com.example.spring09.entity.Emp;
import com.example.spring09.repository.DeptRepository;
import com.example.spring09.repository.EmpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployServiceImpl implements EmployService {
	
	// 의존 객체 주입
	private final EmpRepository	empRepo;
	private final DeptRepository deptRepo;
	
	@Transactional(readOnly =true)
	@Override
	public List<EmpDto> getEmpList() {
		List<EmpDto> empList = empRepo.findAll().stream().map(EmpDto :: toDto).toList();
		
		return empList; // return empRepo.findAll().stream().map(EmpDto::toDto).toList();
	}

	@Transactional(readOnly =true)
	@Override
	public List<DeptDto> getDeptList() {
		List<DeptDto> deptList = deptRepo.findAll().stream().map(DeptDto :: toDto).toList();
		
		return deptList;
	}

	@Transactional(readOnly =true)
	@Override
	public EmpDeptDto getEmpDetail(int empno) {
		// 사원 번호를 이용해서 Emp entity 를 얻어내고
		Emp e = empRepo.findById(empno).get();
		// Emp entity 를 EmpDeptDto 로 변경해서 리턴한다 
		return EmpDeptDto.toDto(e);
	}

	@Transactional(readOnly =true)
	@Override
	public DeptDto getDeptDetail(int deptno) {
		// 한줄 코딩
		return DeptDto.toDto(deptRepo.findById(deptno).get());
	}

}
