package com.example.spring11.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.spring11.anno.HandlerRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * WebSocketHandler 클래스에 등록할 웹소켓 핸들러 객체 
 */
public class DispatchingSocketHandler extends TextWebSocketHandler {
	private final HandlerRegistry registry;
    private final SocketSessionManager sessionManager;
    // json 문자열 <=> object 간의 상호 변환하는 기능을 제공하는 객체 
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    //생성자로 의존 객체를 주입 받아서 필드에 저장하기 
    public DispatchingSocketHandler(HandlerRegistry registry, SocketSessionManager sessionManager) {
        this.registry = registry;
        this.sessionManager = sessionManager;
    }
    
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	// 전송된 json 문자열 읽어오기 
    	String payload = message.getPayload();

    	
        JsonNode root;
        try {
            root = objectMapper.readTree(payload); // JSON 파싱 시도
        } catch (Exception e) {
            // 예외 발생 시 클라이언트에게 에러 메시지 전송 (선택)
            session.sendMessage(new TextMessage("Invalid JSON format: " + payload));
            return; // 더 이상 진행하지 않고 종료 → 소켓 연결은 유지됨
        }

    	// JSON 내에서 "path" 값을 추출 → 어떤 메서드를 호출할지 결정하는 키
    	String path = root.get("path").asText();

    	// "data" 필드가 있으면 가져오고, 없으면 null 처리 (Optional 처리)
    	JsonNode dataNode = root.has("data") ? root.get("data") : null;

    	// path 값으로 등록된 메서드를 HandlerRegistry에서 찾는다
    	Method method = registry.getHandler(path);

    	// 해당 path에 대응하는 메서드가 없으면 에러 메시지를 클라이언트에게 전송 후 종료
    	if (method == null) {
    	    session.sendMessage(new TextMessage("No handler for path: " + path));
    	    return;
    	}

    	// 메서드를 소유한 컨트롤러 객체를 가져온다
    	Object controller = registry.getController(method);

    	// 메서드 파라미터의 타입 목록을 얻어온다
    	Class<?>[] paramTypes = method.getParameterTypes();

    	// 메서드에 넘겨줄 실제 인자 배열을 구성한다
    	Object[] args = Arrays.stream(paramTypes)
    			.map(paramType -> convertParameter(paramType, session, dataNode))
    		    .toArray(); // 최종적으로 Object[] 형태로 파라미터 목록 완성

    	// 준비된 controller와 args를 이용해 해당 메서드를 실행 (invoke)
    	method.invoke(controller, args);
    }
  
    private Object convertParameter(Class<?> type, WebSocketSession session, JsonNode dataNode) {
        if (type.equals(WebSocketSession.class)) {
            return session;
        }

        if (dataNode == null || dataNode.isNull()) {
            return null;
        }

        try {
        	/*
        	 *  {
        	 *  	"path:"xxx",
        	 *  	"data":{
        	 *    
        	 *       }
        	 *  }
        	 *  data 라는 키값으로 전달된 object 형식의 json 문자열을 실제 type 으로 변환해서 리턴해준다.
        	 *  
        	 *  예를들어 
        	 *  
        	 *  "data":{"text":"메세지}   이면
        	 *  
        	 *  {"text":"메세지} 를 ChatMessage 객체로 변환해준다
        	 */
            return objectMapper.treeToValue(dataNode, type);
        } catch (Exception e) {
            throw new RuntimeException("데이터 변환 실패: " + type.getName(), e);
        }
    }	
    
	//클라이언트가 웹소켓 연결을 요청하고 성공되었을때 호출되는 메소드 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionManager.register(session);
	}
	
	//클라이언트의 웹소켓 연결이 종료되면 호출되는 메소드 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {	
		//접속이 종료된 session 을 List 에서 제거 
		sessionManager.remove(session);	
		//접속이 종료된 session 이 대화방에 입장한 session 인지 확인해서 맞으면 Map 에서도 제거 
		String userName=sessionManager.getSessionUser(session);
		//만일 userName 이 null 이 아니라면 대화방에 입장된 userName 이다.
		if(userName != null) {
			//제거한다
			
		}
	}
}