package com.example.spring11.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// TextWebSocketHandler 클래스를 상속 받는다 
public class MySocketHandler extends TextWebSocketHandler {
	
	// WebSocketSession 을 누적 시킬 객체 (멀티 thread 에 안전하지 않은 ArrayList 객체)
	// List<WebSocketSession> sessionList = new ArrayList<>();
	
	// Thread Safe 한 동기화된 리스트 객체 사용하기
	List<WebSocketSession> sessionList = Collections.synchronizedList(new ArrayList<>());
	
	// 클라이언트가 웹소켓 연결을 요청하고 성공되었을때 호출되는 메소드 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 접속된 클라이언트의 WebSocketSession 을 List 에 추가하기
		sessionList.add(session);
		// 접속된 클라이언트에게 환영 메세지를 보내기 
		TextMessage msg=new TextMessage("안녕 클라이언트야! 웹소켓 접속을 환영해~");
		session.sendMessage(msg);
	}
	
	// 클라이언트가 text 메세지를 보내면 호출되는 메소드 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 전달된 TextMessage 를 모든 클라이언트에게 중계(broadcast) 해주기
		sessionList.forEach(item->{
			// item 은 WebSocketSession 객체이다
			try {
				// WebSocketSession 객체에 message 를 담고 
				item.sendMessage(message);
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	// 클라이언트가 웹소켓 연결이 종료되면 호출되는 메소드 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 연결해제된 클라이언트의 WebSocketSession 을 List 에서 찾아서 제거하기 
		sessionList.remove(session);
	}
}
