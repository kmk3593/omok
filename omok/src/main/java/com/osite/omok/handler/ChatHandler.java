package com.osite.omok.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> list = new ArrayList<>();
	
	/**
	 * websocket 에 내재된 함수를 사용할 경우, 호출된다 
	 * 예시) websocket.send
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		
		String payload = message.getPayload();
		System.out.println("payload : " + payload);
		
		// payload란 전송되는 데이터를 의마한다 
		for(WebSocketSession sess: list) {
			sess.sendMessage(message);
		}
	}
	
	/** 설명 테스트
	 * Client가 접속 시 호출되는 메서드
	 */ 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		list.add(session);
		System.out.println(session + " 클라이언트 접속");
	}
	
	/* Client 가 접속 해제 시 호출되는 메서드 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println(session + " 클라이언트 접속 해제");
		list.remove(session);
	}
	
	
	
	
	
}
