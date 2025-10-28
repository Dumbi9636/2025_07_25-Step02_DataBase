package com.example.spring11.anno;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
 * WebSocket 용 컨트롤러 클래스와 핸들러 메소드를 등록하고,
 * path 기반으로 해당 메소드를 찾아주는 레지스트리 클래스
 */
public class HandlerRegistry {

    // "path" → 해당 path를 처리하는 메서드
    private final Map<String, Method> handlerMap = new HashMap<>();

    // 메서드 → 해당 메서드를 포함한 컨트롤러 객체
    private final Map<Method, Object> controllerMap = new HashMap<>();

    /*
     * @SocketController 가 붙은 객체를 등록하는 메서드
     * 이 안에서 메서드마다 @SocketMapping 이 붙은 것들을 추출하여 매핑
     */
    public void register(Object controller) {
        Class<?> clazz = controller.getClass();

        // 클래스에 @SocketController 어노테이션이 없으면 무시
        if (!clazz.isAnnotationPresent(SocketController.class)) return;

        // 클래스의 모든 메서드를 순회
        for (Method method : clazz.getDeclaredMethods()) {
            // @SocketMapping 어노테이션이 붙은 메서드만 등록
            if (method.isAnnotationPresent(SocketMapping.class)) {
                // 어노테이션에서 path 값을 읽어옴
                String path = method.getAnnotation(SocketMapping.class).value();

                // path → 메서드 등록
                handlerMap.put(path, method);

                // 해당 메서드 → 컨트롤러 인스턴스 등록
                controllerMap.put(method, controller);
            }
        }
    }

    /*
     * 클라이언트가 보낸 path 값으로 해당 메서드를 찾아서 반환
     */
    public Method getHandler(String path) {
        return handlerMap.get(path);
    }

    /*
     * 메서드 → 해당 메서드를 포함하는 컨트롤러 인스턴스를 반환
     */
    public Object getController(Method method) {
        return controllerMap.get(method);
    }
}
