package com.osite.omok.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osite.omok.dto.OmokRoomDTO;
import com.osite.omok.dto.TransferMessage;
import com.osite.omok.dto.TransferMessage.messageType;
import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;
import com.osite.omok.service.OmokService;
import com.osite.omok.service.UserService;

import ch.qos.logback.core.subst.Token.Type;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OmokSocketHandler extends TextWebSocketHandler {
	
	private final OmokService omokService;
	private final UserService userService;
	
	//  ObjectMapper : json을 object로 바꿔주거나 object형태를 json으로 바꿀때 사용하는 객체
	private final ObjectMapper objectMapper;
	
	@Override // 연결 했을때
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			System.out.printf("%s 연결됨\n",session.getId());
	}

	@Override // 연결 끊을때
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.printf("%s 연결 끊김\n",session.getId());
//		Principal principal = session.getPrincipal();
//		System.out.println(principal.toString());
//		System.out.println(principal.getName());
//		System.out.println(status.toString());
		
		OmokRoomDTO room = omokService.quitRoom(session, status);
		if (omokService.gameEndYN(room.getRoomID())) {
			return;
		}
		
		
		UserTable sender = userService.getUserInfo(session.getPrincipal().getName());
		Set<WebSocketSession> sessions = room.getSessions();
		
		TransferMessage transferMessage = new TransferMessage();
		transferMessage.setType(messageType.QUIT);
		transferMessage.setRoomID(room.getRoomID());
		transferMessage.setSender(sender.getUserNum());
		
		sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(transferMessage)));

		// System.out.println(status.getReason());
		// 그냥 뒤로가기 눌렀을때
		// CloseStatus[code=1001, reason=null]
		// 서버 중단했을때
		// CloseStatus[code=1001, reason=The web application is stopping]
		// 창을 닫을때
		// CloseStatus[code=1001, reason=null]
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.printf("%s로부터 [%s]받음\n", session.getId(), message.getPayload());
		System.out.println();
		
		
		// payload는 서버와 주고받을 실제 데이터
		String payload = message.getPayload();
		
		// ObjectMapper를 이용해 json형태의 message를 transferMessage형태로 변형
		TransferMessage transferMessage = objectMapper.readValue(payload, TransferMessage.class);
		OmokRoomDTO room = omokService.findRoomByID(transferMessage.getRoomID());
		
		Set<WebSocketSession> sessions = room.getSessions();
		

		if (transferMessage.getType().equals(TransferMessage.messageType.ENTER)) {
			// 메시지 타입이 ENTER의 경우
			sessions.add(session);
			transferMessage.setMessage(transferMessage.getSender()+"번 유저가 입장하셨습니다.");
			sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(transferMessage)));
		} else if (transferMessage.getType().equals(TransferMessage.messageType.QUIT)) {
			// 메시지 타입이 QUIT의 경우
			System.out.println("나가따!");
			sessions.remove(session);
			if (sessions.size() == 0) {
				omokService.deleteRoomByID(room.getRoomID());
			}
            transferMessage.setMessage(transferMessage.getSender()+"번 유저가 퇴장하셨습니다.");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(transferMessage)));
		} else if (transferMessage.getType().equals(TransferMessage.messageType.TALK)) {
			// 메시지 타입이 TALK의 경우
			sendToEachSocket(sessions,message);
		} else if (transferMessage.getType().equals(TransferMessage.messageType.PLACE)) {
			// 메시지 타입이 PLACE(착수)의 경우
			if (room.getSessions().size()!=2) {
				TransferMessage newMessage = messageSetting(
						"TALK", transferMessage.getRoomID()
						, transferMessage.getSender()
						, "아직 상대방이 들어오지 않았습니다");
				sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(newMessage)));
			} else {
				omokService.placedStone(transferMessage);
				sendToEachSocket(sessions,message);
			}
			
		} else if (transferMessage.getType().equals(TransferMessage.messageType.GIVEUP)) {
			Boolean result = omokService.giveUp(transferMessage);
			if (result==true) {
				sendToEachSocket(sessions,message);
			}
		} else if (transferMessage.getType().equals(TransferMessage.messageType.WIN)) {
			System.out.println("승리 받았고");
			System.out.println(transferMessage.toString());
			omokService.gameWin(transferMessage);
		}
	}
	
	
	// 각각의 session별 소켓으로 메세지 전송
	private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
		sessions.parallelStream().forEach(roomSesstion ->{
			try {
				roomSesstion.sendMessage(message);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});;
	
	}
	
	private TransferMessage messageSetting(String type, String roomID
			, Integer sender, String message) {
		TransferMessage transferMessage = new TransferMessage();
		if (type == "TALK") {
			transferMessage.setType(messageType.TALK);
		}
		transferMessage.setRoomID(roomID);
		transferMessage.setSender(sender);
		transferMessage.setMessage(message);
		
		return transferMessage;
	}
	
	
	
}