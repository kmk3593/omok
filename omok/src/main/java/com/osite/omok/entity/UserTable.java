package com.osite.omok.entity;

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
public class UserTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userNum;
	
	// ID
	// @Column(unique = true)
	private String username;
	
	// 비밀번호
	private String password;
	
	// 닉네임
	// @Column(unique = true)
	private String nickname;
	
	// 승리 획수
	private Integer winLate;
	
	// 패배 횟수
	private Integer loseLate;
	
	// 최종 로그인 날짜
	private LocalDateTime lastLoginDate;

}
