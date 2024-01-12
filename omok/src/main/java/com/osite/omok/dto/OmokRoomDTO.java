package com.osite.omok.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OmokRoomDTO {

	// 방 번호
	private String roomID;
	
	// 방이 보여지는 이름
	private String name;
	
	// 세션으로 구분할 방 Set
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	// @builder는 생성자와 같은 역할
	// 다만 쓰는 이유를 찾아보자면
	// 1. 파라미터가 많을경우 가독성이 좋지않다.
	// 2. 생성자는 속성값의 순서가 중요하지만 빌더패턴은 얽메이지 않는다.
	// 나의 생각 : 쓸대없는 시간낭비였다...
	@Builder
	public OmokRoomDTO(String roomID, String name) {
		this.roomID = roomID;
		this.name = name;
	}
	
}
