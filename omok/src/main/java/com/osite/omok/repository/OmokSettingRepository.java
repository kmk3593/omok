package com.osite.omok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;

public interface OmokSettingRepository extends JpaRepository<OmokSetting, Integer>{

	List<OmokSetting> findBywhiteStone(UserTable whiteStone);
	
	void deleteBygameNum(Integer gameNum);
	
	void deleteByroomID(String roomID);
	
//	void deleteByID
	
	OmokSetting findByroomID(String roomID);
}