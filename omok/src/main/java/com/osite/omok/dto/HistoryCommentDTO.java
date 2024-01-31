package com.osite.omok.dto;

import java.time.LocalDateTime;

import com.osite.omok.entity.HistoryComment;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryCommentDTO extends HistoryComment {
	
	// 댓글 인식 번호
	private Integer historyCommentNum;
	
	// 작성자
	private Integer userNumUserNum;
	
	// 게임 번호
	private Integer gameNumGameNum;
	
	// 댓글 내용
	private String comment;
	
	// 작성일시
	private LocalDateTime writeDateTime;
}
