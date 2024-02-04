package com.osite.omok.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.UserTableRepository;
import com.osite.omok.service.ChatService;
import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> list = new ArrayList<>();
	private final UserTableRepository userTableRepository;
	private final ChatService chatService;
	
	/**
	 * websocket 에 내재된 함수를 사용할 경우, 호출된다 
	 * 예시) websocket.send
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		
		String payload = message.getPayload();
		System.out.println("))))))))))))))))))))))))))))))))payload : " + payload);
		// 1. 채팅 서비스 쪽에 저장하는 함수를 생성
		// 2. 그 함수는 jpa repository를 이용한다
		// 3. 그 함수를 이 곳에 호출해서 채팅이 보내질 때, 채팅내역을 저장한다.
		// 저장할 거 = usernum 으로 검색해서, 채팅 친 사람 , 채팅 내용, 시간 저장
		
		System.out.println("session = " + session);
		System.out.println("session get = " + session.getPrincipal());
		
		String username = session.getPrincipal().getName();
		
		Optional<UserTable> loginUser = userTableRepository.findByusername(username);
		
		UserTable userTable = loginUser.get();
		
		chatService.chatSave(userTable, payload);
		
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
