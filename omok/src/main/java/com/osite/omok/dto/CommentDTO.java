package com.osite.omok.dto;

import java.time.LocalDateTime;

import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO{

	// 댓글 인식 번호
	private Integer commentNum;
	
	// 작성자
	private UserTable userNum; 
	
	// 본문 번호
	private Board boardNum;
	
	// 댓글 내용
	private String comment;
	
	// 작성일시
	private LocalDateTime writeDateTime;	
	

}
