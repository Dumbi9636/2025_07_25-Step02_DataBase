package com.example.spring11.config;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.spring11.anno.HandlerRegistry;
import com.example.spring11.anno.SocketController;
import com.example.spring11.handler.DispatchingSocketHandler;
import com.example.spring11.handler.MySocketHandler;
import com.example.spring11.handler.SocketSessionManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration // 설정을 위한 어노테이션
@EnableWebSocket // 웹소켓을 사용하기 위한 어노테이션 
public class WebSocketConfig implements WebSocketConfigurer{
	// 의존 객체 생성자 주입을 위한 final 필드 선언 
	private final ApplicationContext applicationContext;
	private final SocketSessionManager sessionManager;
	
	// 웹소켓 핸들러를 등록하는 메소드(websocket 요청 처리)  
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		//ApplicationContext 객체를 이용해서 @SocketController 어노테이션이 붙어 있는 객체를 모두 가져온다음
		Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(SocketController.class);
		//우리가 설계한 HandlerRegistry 객체 생성해서 반복문 돌면서 모두 등록한다
		HandlerRegistry handlerRegistry=new HandlerRegistry();
		for (Object controller : controllers.values()) {
			handlerRegistry.register(controller);
		}
		
		// "/ws" 경로는 MysocketHandler 객체로 처리하기
		registry.addHandler(new MySocketHandler(), "/ws")
			.setAllowedOrigins("*");
		
		// "/ws" 경로의 웹소켓 연결 요청이 들어오면 MySocketHandler 객체로 처리하겠단 의미 
		registry.addHandler(new DispatchingSocketHandler(handlerRegistry, sessionManager), "/ws2")
		.setAllowedOrigins("*"); // 개발중에는 CORS 허용 (어디서든지 접속이 가능하도록)
	}
}
