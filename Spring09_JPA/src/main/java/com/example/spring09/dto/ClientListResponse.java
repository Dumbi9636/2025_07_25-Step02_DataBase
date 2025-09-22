package com.example.spring09.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientListResponse {
	private List<ClientDto> list;
	private int startPageNum;
	private int endPageNum;
	private int totalPageCount;
	private int PageNum;
}
