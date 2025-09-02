package com.example.spring09.service;

import java.time.LocalDate;
import java.util.List;


import com.example.spring09.dto.ClientDto;


public interface ClientService  {
	/** 회원 정보 추가 (생성 후 PK 반환) */
	Long addClient(ClientDto dto);
	
	/** 회원 목록 조회 */
	List<ClientDto> getClients();
	
	/** 개인정보 수정 페이지용 : 단건 조회 */
	ClientDto getClient(Long num);
	
	/** 생일 입력/수정 (개인정보 수정) */
	void updateBirthday(Long num, LocalDate birthday);
}
