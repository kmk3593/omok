package com.osite.omok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferMessage {
	// 입장, 대화, 퇴장, 착수
	public enum messageType{
		ENTER, TALK, QUIT, PLACE, GIVEUP, WIN
	}
	
	private messageType type;
	private String roomID;
	private Integer sender;
	private String message;
	private Integer xLine;
	private Integer yLine;
	
}
