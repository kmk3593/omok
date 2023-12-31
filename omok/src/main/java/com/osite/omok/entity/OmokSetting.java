package com.osite.omok.entity;

import jakarta.annotation.Nullable;
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
public class OmokSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gameNum;
	
	// 승자
	@ManyToOne
	@Nullable
	private UserTable winner;
	
	// 패자
	@ManyToOne
	@Nullable 
	private UserTable loser;
	
}
