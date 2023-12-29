package com.osite.omok;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userNum;
	
	// 최종 로그인 날짜
	private LocalDateTime lastLoginDate;
	
	private String username;
	
	private String password;
	
	// 닉네임
	private String nickname;
	
	// 승리 획수
	private Integer winLate;
	
	// 패배 횟수
	private Integer loseLate;
	
	

}
