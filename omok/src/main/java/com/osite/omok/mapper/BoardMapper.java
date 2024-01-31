package com.osite.omok.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.dto.BoardDetialDTO;
import com.osite.omok.entity.Board;
import com.osite.omok.entity.HistoryComment;

@Mapper
public interface BoardMapper {
	void insertBoard(Board board);
	
	BoardDetialDTO getBoardDetail(Integer boardNum);
	
}
