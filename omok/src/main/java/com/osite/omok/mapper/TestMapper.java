package com.osite.omok.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;

@Mapper
public interface TestMapper {
	UserTable testByID(String username);
	
	List<Board> selectBoard(Board board);
//	Board selectBoard(Board board);
	
	void insertTest(Board board);
	
}
