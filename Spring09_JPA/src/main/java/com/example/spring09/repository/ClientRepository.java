package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring09.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> { // entity 와  entity 에서 id 역할을 하는 type : Long 
	public List<Client> findAllByOrderByNumDesc();
	public List<Client> findAllByOrderByuserNameDesc();
	
	@Query("SELECT c FROM CLIENT_INFO c ORDER BY c.num DESC")
	public List<Client> findAllQuery();
}
