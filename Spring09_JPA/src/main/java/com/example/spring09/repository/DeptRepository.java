package com.example.spring09.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring09.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer> {

}
