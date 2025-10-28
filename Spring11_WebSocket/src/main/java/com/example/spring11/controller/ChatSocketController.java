package com.example.spring11.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.spring11.anno.SocketController;
import com.example.spring11.anno.SocketMapping;
import com.example.spring11.dto.ChatMessage;
import com.example.spring11.handler.SocketSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@SocketController
@RequiredArgsConstructor
//특정 경로로 웹소켓 요청이 오면 요청을 처리할 컨트롤러
public class ChatSocketController {
	// 얼마든지 원하는 메소드를 만들 수 있다.
	
	// 의존 객체 생성자 주입 받기
	private final SocketSessionManager sessionManager;
	
	// 객체 <=> json 상호 변경할 수 있는 객체 (obejct mapper)
	ObjectMapper mapper = new ObjectMapper();
	
	// 클라이언트의 웹소켓 대화방 입장 요청을 처리할 메소드
	@SocketMapping("/chat/enter")
	public void chatEnter(WebSocketSession session, ChatMessage message) {
		// 대화방에 입장하는 userName
		String userName = message.getUserName();
		// 누가 어떤 session 으로 입장했는지 저장하기
		sessionManager.enterUser(userName, session);
		// 대화방에 입장한 모든 사용자 목록
		List<String> userList = sessionManager.getAllUserNames();
		// 위 정보를 JSON 문자열로 바꿔서 전송해야한다. 
		// 대표적으로 Object Mapper
		
		// JSON 문자열로 변경할 내용을 일단 Map 객체에 담는다 
		Map<String, Object> map =Map.of(
				"type", "enter", // key , value
				"payload", Map.of(  // key, value
					"userName", userName,
					"userList", userList
				)
		);
		
		// ObjectMapper 객체를 이용해서 Map 에 담긴 내용을 json 문자열로 변환한다
		String json="{}";
		try {
			/*
			 	map 은 { } 중괄호로 변환되고, List 는 [ ] 대괄호로 변환된다.
			 	List 의 경우 generic 이 stirng 타입이기 때문에 "대화명1" 과 같은 문자열이 들어간다. 
			 
			  변환된 json 문자열의 구조는 아래와 같다
			  {
			  	"type" : "enter",
			  	"payload : {
			  		"userName":"대화명",
			  		"userList":["대화명1", "대화명2", "대화명3", ...]
			  	}
			  }
			  
			*/
			json=mapper.writeValueAsString(map);
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		// 대화방에 입장한 모든 클라이언트에게 전송할 json 문자열을 TextMessage 객체에 담아서 
		TextMessage msg = new TextMessage(json);
		// session manager 객체의 메소드를 이용해서 전송한다
		sessionManager.broadcast(msg);
	}
	
	
	@SocketMapping("/chat/send")
	public void chatPublic(WebSocketSession session, ChatMessage message) {
		//System.out.println("/chat/send");
		//System.out.println(message);
		
		// 웹소켓 접속된 모든 클라이언테으게 대화 메세지를 전송한다 
		sessionManager.broadcast(new TextMessage(message.getText()));
	}
	
	
}
