package com.osite.omok.Entity;

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
public class Board {

	// 인식 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardNum;
	
	// 제목
	@Column(length = 30)
	private String title;
	
	// 본문
	@Column(columnDefinition = "TEXT")
	private String text;
	
	// 작성자
	@ManyToOne
	private UserTable usernum;

	// 작성일시
	private LocalDateTime writeDateTime;
	
}
