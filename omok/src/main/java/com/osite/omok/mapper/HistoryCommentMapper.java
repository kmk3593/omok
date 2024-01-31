package com.osite.omok.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.dto.HistoryCommentDTO;
import com.osite.omok.entity.HistoryComment;

@Mapper
public interface HistoryCommentMapper {
	
	List<HistoryCommentDTO> getHistoryCommentList(Integer gameNum);
	
	int saveComment(HistoryComment historyComment);

	int deleteComment(HistoryComment historyComment);

	int updateComment(HistoryComment historyComment);

}
