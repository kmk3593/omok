package com.osite.omok.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.dto.OrderDTO;
import com.osite.omok.dto.SettingDTO;
import com.osite.omok.entity.OmokOrder;
import com.osite.omok.entity.OmokSetting;

@Mapper
public interface OmokSettingMapper {
	
	List<SettingDTO> getAllOmokHistoryList();

	SettingDTO findOmokSetting(Integer gameNum);
	
}
