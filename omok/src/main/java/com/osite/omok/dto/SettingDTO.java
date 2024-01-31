package com.osite.omok.dto;

import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SettingDTO extends OmokSetting {
	
	private Integer gameNum;
	
	// 흑돌 유저
	private Integer blackStoneUserNum;
	
	// 백돌 유저
	private Integer whiteStoneUserNum;
	
	// 방을 개별적으로 인식하게하는 아이디
	private String roomID;
	
	// 방이 보여지는 이름 
	private String roomName;
	
	// 승리한 유저
	private Integer winnerUserNum;
	
	// 패배한 유저
	private Integer loserUserNum;

}
