package com.osite.omok.dto;

import java.time.LocalDateTime;

import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class BoardDetialDTO extends Board{
	
	// 인식 번호
	private Integer boardNum;
	
	// 제목
	private String title;
	
	// 본문
	private String text;
	
	// 작성자
	private Integer writerUserNum;

	// 작성일시
	private LocalDateTime writeDateTime;

	
}
