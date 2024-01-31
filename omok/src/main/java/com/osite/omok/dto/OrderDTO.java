package com.osite.omok.dto;

import com.osite.omok.entity.OmokOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO extends OmokOrder {

	// PK
    private Integer orderNum;

    // 게임 번호
    private Integer gameNumGameNum;

    // 둔 사람
    private Integer userNumUserNum;

    // x 축
    private Integer xLine;

    // y 축
    private Integer yLine;

    // 수순
    private Integer procedure;	
	
}
