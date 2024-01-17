package com.osite.omok.entity;

import org.springframework.web.socket.WebSocketSession;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OmokSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gameNum;
	
	// 흑돌 유저
	@ManyToOne
	@Nullable
	private UserTable blackStone;
	
	// 백돌 유저
	@ManyToOne
	@Nullable
	private UserTable whiteStone;
	
	// 방을 개별적으로 인식하게하는 아이디
	private String roomID;
	
	// 방이 보여지는 이름 
	private String roomName;
	
	// 승리한 유저
	@ManyToOne
	@Nullable
	private UserTable winner;
	
	// 패배한 유저
	@ManyToOne
	@Nullable
	private UserTable loser;
	
	// 흑돌 세션
//	private String blackSession;
	
	// 백돌 세션
//	private String whiteSession;
	
	// @builder는 생성자와 같은 역할
	// 다만 쓰는 이유를 찾아보자면
	// 1. 파라미터가 많을경우 가독성이 좋지않다.
	// 2. 생성자는 속성값의 순서가 중요하지만 빌더패턴은 얽메이지 않는다.
	// 나의 생각 : 쓸대없는 시간낭비였다...
//	@Builder
//	public OmokSetting(String roomID
//			, String name
//			, UserTable blackStone) {
//		this.roomID = roomID;
//		this.roomName = name;
//		this.blackStone = blackStone;
//	}
	
}
