package com.example.spring10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HomeController {
	
	@GetMapping("/notice")
	public List<String> notice(){ // 공지사항은 로그인 없이 볼 수 있도록 Security config 에서 whitelist 에 추가 
		return List.of("수요일인 내일(24일)은 전국이 대체로 흐리고 비가 오겠습니다.\r\n"
				+ "\r\n"
				+ "23일 남부지방에 내리기 시작한 비는 24일 늦은 새벽 충남 서해안과 전북 서해안으로, 오전 전국으로 확대돼 25일까지 이어지겠습니다.\r\n"
				+ "\r\n"
				+ "예상 강수량은 서해5도와 전라권 30∼80㎜(많은 곳 100㎜ 이상), 서울·인천·경기와 강원 내륙·산지, 충청권, 부산·울산·경남 20∼60㎜(많은 곳 80㎜ 이상), 제주도 10∼60㎜, 울릉도·독도 10∼40㎜이며, 강원 북부 동해안은 5∼20㎜, 강원 중·남부 동해안은 5㎜ 안팎의 비가 오겠습니다.\r\n"
				+ "\r\n"
				+ "비와 함께 돌풍이 불거나 천둥·번개가 치는 곳이 있겠고 수도권과 충청권, 강원은 오전과 밤사이 시간당 20∼30㎜씩 강한 비가 쏟아질 때가 있겠습니다.\r\n"
				+ "\r\n"
				+ "아침 최저기온은 17∼23도, 낮 최고기온은 22∼31도로 예보됐고, 남부지방은 최고 체감온도가 31도 안팎으로 오르면서 무더운 곳이 있겠습니다.\r\n"
				+ "\r\n"
				+ "미세먼지 농도는 원활한 대기 확산과 강수의 영향으로 전 권역이 '좋음' 수준을 보이겠습니다.");
	}
}
