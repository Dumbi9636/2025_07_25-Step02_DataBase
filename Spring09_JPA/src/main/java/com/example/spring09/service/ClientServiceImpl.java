package com.example.spring09.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.ClientDto;
import com.example.spring09.entity.Client;
import com.example.spring09.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
	// 의존객체 생성자 주입
	private final ClientRepository clientRepo;
	
	
	// Client 정보 저장
	@Transactional
	@Override
	public Long addClient(ClientDto dto) {
		// dto 를 entity 로 변경해서 저장하고 리턴되는 값은 방금 저장한 Client entity 객체(saved)가 리턴된다.
		Client saved = clientRepo.save(dto.toEntity()); // dto를 entity 로 바꾼다음 saved 에 저장해서 리턴
		
		// entity 에 들어있는 번호를 리턴
		return saved.getNum();
	}
	
	
	// Client 목록 조회
	@Transactional(readOnly = true)
	@Override
	public List<ClientDto> getClients() {
		// entity List 를 stream 으로 만들어서 map() 함수를 이용해서 dto 의 stream 으로 만든다음 
		// dto List 로 변경하기 
		// [findAll() -> entity 의 list를 찾음] -> 
		// [.stream() -> entity 의 stream 으로 바꿈] -> 
		// [.map() -> dto 의 stream 으로] ->  한줄코딩을 위해 map 함수를 사용하고, map 함수를 사용하기 위해 stream 함수를 사용한다. 
		// [.toList() -> dto 의 List 로 변경] 
		List<ClientDto> list = clientRepo.findAll().stream().map(ClientDto :: toDto).toList();
			
		return list;
	}
	
	
	// 힌명의 고객 정보 조회
	@Transactional(readOnly = true)
	@Override
	public ClientDto getClient(Long num) {
		// Client entity = clientRepo.findById(num).get();
		
		Client entity = clientRepo.findById(num)
				.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 고객입니다 num="+num));
		// entity 를 dto 로 변경해서 리턴한다
		return ClientDto.toDto(entity);
	}

	
	@Transactional
	@Override
	public void updateBirthday(Long num, LocalDate birthday) {
		// 번호를 이용해서 entity 를 갖고온다.
		Client entity = clientRepo.findById(num).get();
		// 생일 날짜를 넣어준다
		entity.setBirthday(birthday); // entity 를 수정하는 것만으로 자동으로 반영된다.
	}
}
