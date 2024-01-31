package com.osite.omok.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.dto.OrderDTO;

@Mapper
public interface OmokOrderMapper {
	
	List<OrderDTO> getOmokOrdersFromGameNum(Integer gameNum);
	
}
