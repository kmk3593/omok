package com.osite.omok.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OmokOrder {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNum;

    // 게임 번호
    @ManyToOne
    private OmokSetting gameNum;

    // 둔 사람
    @ManyToOne
    private UserTable userNum;

    // x 축
    private Integer xLine;

    // y 축
    private Integer yLine;

    // 수순
    private Integer procedure;	
}
