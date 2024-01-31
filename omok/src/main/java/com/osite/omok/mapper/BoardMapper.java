package com.osite.omok.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.dto.BoardDetialDTO;
import com.osite.omok.entity.Board;
import com.osite.omok.entity.HistoryComment;

@Mapper
public interface BoardMapper {
	
	/**
	 *	게시글 작성 
	**/
	void insertBoard(Board board);
	
	/**
	 * 	게시글 상세보기
	**/
	BoardDetialDTO getBoardDetail(Integer boardNum);
	
	/**
	 * 	게시글 삭제하기
	*/
	void deleteBoard(Integer boardNum);
	
	/**
	 * 	게시글 수정하기
	*/
	void updateBoard(Board board);
	
}
