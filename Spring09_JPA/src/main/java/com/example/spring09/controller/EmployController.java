package com.example.spring09.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;
import com.example.spring09.service.EmployService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployController {
	
	// 의존 객체 주입
	private final EmployService employService;
	
	// 사원 목록 불러오기
	@GetMapping("/emps")
	public String empList(Model model) {
		model.addAttribute("empList", employService.getEmpList());
		return "emps/list";
	}
	
	@GetMapping("/emps/{empno}")
	public String empDetail(@PathVariable int empno, Model model) {
		
		// 사원의 자세한 정보
		EmpDeptDto dto = employService.getEmpDetail(empno);
		// 응답에 필요한 데이터를 "emp"라는 키값으로 담기
		model.addAttribute("emp", dto);
		return "emps/detail";
	}
	
	@GetMapping("/depts")
	public String deptList(Model model) {
		// 부서 목록
		List<DeptDto> deptList = employService.getDeptList();
		model.addAttribute("deptList", deptList);
		
		return "depts/list";
	}
	
	@GetMapping("/depts/{deptno}")
	public String deptDetail(@PathVariable int deptno, Model model) {
		// 경로변수에 전달된 부서번호를 이용해서 부서의 자세한 정보를 얻어와서
		DeptDto dto = employService.getDeptDetail(deptno);
		// Model 객체에 담고
		model.addAttribute("dept", dto);
		
		// 해당 부서에 근무하는 사원의 정보도 얻어와서 Model 객체에 같이 담아준다
		List<EmpDto> empList = employService.getEmpListByDeptno(deptno);
		model.addAttribute("empList", empList);
		// 응답하기
		return "depts/detail";
	}
	
	
}
