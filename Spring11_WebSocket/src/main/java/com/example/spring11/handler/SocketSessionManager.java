package com.example.spring11.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
// 웹소켓에 접속한 모든 클라이언트의 session 을 관리
@Component // bean
public class SocketSessionManager {
	// Thread Safe 한 동기화된 리스트 객체 사용하기 (웹소켓 접속한 모든 클라이언트의 session 이 저장되어 있다)
	List<WebSocketSession> sessionList=Collections.synchronizedList(new ArrayList<>());
	/*
	 *  userName <=> SocketSession  를 저장하기 위한 Map
	 *  ConcurrentHashMap 객체도 Thread Safe 한 동기화된 Map 객체 
	 *  ( 대화방에 입장한 모든 클라이언트의 session 과 userName 이 저장되어 있다)
	 */
	Map<String, WebSocketSession> userSessions=new ConcurrentHashMap<>(); // 문자열로 웹소켓 찾기 김구라 -> 소켓
	Map<WebSocketSession, String> sessionUsers=new ConcurrentHashMap<>(); // 웹소켓으로 문자열 찾기 소켓 -> 원숭이
	
	//대화방에 참여한 모든 userName 목록을 리턴하는 메소드
	public List<String> getAllUserNames(){
		//Map 에 있는 모든 key(userName) 값을  Set<String> 으로 얻어내기
		Set<String> keySet=userSessions.keySet();
		//Set 에 들어 있는 내용을 이용해서 List 얻어내기
		List<String> userList=new ArrayList<String>(keySet);
		return userList;
		
	}
	
	//대화방에 참여한 user 의 session 을 저장하는 메소드 
	public void enterUser(String userName, WebSocketSession session) {
		userSessions.put(userName, session);
		sessionUsers.put(session, userName);
	}
	
	//userName 를 전달하면 해당 Session 을 리턴해주는 메소드 
	public WebSocketSession getUserSession(String userName) {
		return userSessions.get(userName);
	}
	
	// session 을 전달하면 해당 session 을 사용하는 userName 을 리턴해주는 메소드 
	public String getSessionUser(WebSocketSession session) {
		return sessionUsers.get(session);
	}
	
	//모든 user session 정보를 리턴하는 메소드
	public Map<String, WebSocketSession> getAllUserSession(){
		return userSessions;
	}

	// 세션을 저장하는 메소드
	public void register(WebSocketSession session) {
		sessionList.add(session);
	}
	
	// 세션을 제거하는 메소드
	public void remove(WebSocketSession session) {
		sessionList.remove(session);
	}
	
	// 세션을 목록을 리턴하는 메소드 
	public List<WebSocketSession> getSessions(){
		return sessionList;
	}
	
	// 대화방에 입장한 모든 session 에 TextMessage 를 중계하는 메소드 
	public void broadcast(TextMessage msg) {
		// 모든 세션을 순회하면서 TextMessage 를 보내기
		sessionList.forEach(item->{
			try {
				item.sendMessage(msg);
			}catch(IOException ie){
				ie.printStackTrace();
			}
		});
	}

	
	
}