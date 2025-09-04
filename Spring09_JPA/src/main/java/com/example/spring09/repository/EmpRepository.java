package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring09.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer> {
	
	// 사원 이름에 대해서 오름차순 정렬된 결과를 리턴하는 메소드 추가
	public List<Emp> findAllByOrderByEnameAsc();
}
