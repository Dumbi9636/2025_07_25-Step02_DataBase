package com.example.spring09.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring09.dto.ClientDto;
import com.example.spring09.dto.ClientListRequest;
import com.example.spring09.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("v3")
public class RestClientController {
	
	// 의존 객체 매핑
	private final ClientService clientService;
	
	// 고객 목록 띄우기
	@GetMapping("/clients")
	public List<ClientDto> list(ClientListRequest request) {
		return clientService.getClients();
	}
	
	// 고객 상세보기 요청  
	@GetMapping("/clients/{num}")
	public ClientDto detail(@PathVariable Long num) {
	    return clientService.getClient(num);
	}
	
	// 고객 추가
	@PostMapping("/clients")
	public ClientDto create(@Valid @RequestBody ClientDto dto) {
	    Long id = clientService.addClient(dto);
	    dto.setNum(id);
	    return dto;
	}

	// 고객 수정
	@PutMapping("/clients/{num}")
	public ClientDto update(@PathVariable Long num, @Valid @RequestBody ClientDto dto) {
	    dto.setNum(num); // 경로의 num을 dto에도 세팅
	    clientService.update(dto);
	    return dto;
	}
	
	
}
