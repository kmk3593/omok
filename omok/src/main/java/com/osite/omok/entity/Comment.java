package com.osite.omok.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Comment {

	// 댓글 인식 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentNum;
	
	// 작성자
	@ManyToOne
	private UserTable userNum; 
	
	// 본문 번호
	@ManyToOne
	private Board boardNum;
	
	// 댓글 내용
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	// 작성일시
	private LocalDateTime writeDateTime;
	
}
