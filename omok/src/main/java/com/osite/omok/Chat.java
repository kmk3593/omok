package com.osite.omok;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chatNum;
	
	// 유저 고유번호
	@ManyToOne
	private User userNum;
	
	// 내용
	@Column(length = 200) 
	private String content;
	
	// 입력 시간
	private LocalDateTime datetime;

}
 