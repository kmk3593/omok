package com.osite.omok.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.osite.omok.dto.BoardDetialDTO;
import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;
import com.osite.omok.mapper.BoardMapper;
import com.osite.omok.mapper.UserTableMapper;
import com.osite.omok.repository.BoardRepository;
import com.osite.omok.repository.UserTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final UserTableRepository userTableRepository;
	private final BoardMapper boardMapper;
	private final UserTableMapper userTableMapper;
	
	/**
	 * 게시글 작성 
	**/
	public void boardCreate(String title, String text, Principal principal) {
		Board board = new Board();
		board.setTitle(title);
		board.setText(text);
		
		// principal을 통해 로그인한 유저의 id를 획득.
		Optional<UserTable> _userTable = userTableRepository.findByusername(principal.getName());
		UserTable userTable = _userTable.get();
		board.setWriter(userTable);
		
		// 현재 시간 저장
		LocalDateTime createTime = LocalDateTime.now();
		board.setWriteDateTime(createTime);
		
		// jpaRepository 사용한 버전
//		boardRepository.save(board);
		
		// myBatis 사용한 버전
		boardMapper.insertBoard(board);
		
	}
	
	/**
	 * 	게시글 상세 보기
	**/
	public Board getBoardDetail(Integer boardNum){
		
		// jpaRepository 사용한 버전
//		Board board = boardRepository.findByboardNum(boardNum);
		
		// myBatis 사용한 버전
		BoardDetialDTO boardDetail = boardMapper.getBoardDetail(boardNum);
		Board board = boardDetail; 
		UserTable userTable = userTableMapper.findByUserNum(boardDetail.getWriterUserNum());
		board.setWriter(userTable);
		
		return board;
	}
	
	/**
	 *	게시글 삭제하기
	*/
	public void deleteBoard(Integer boardNum) {
		
		boardMapper.deleteBoard(boardNum);
		
	}
	
	/**
	 * 	게시글 수정하기
	*/
	public void updateBoard(Integer boardNum, String title, String text) {
		
		Board board = new Board();
		board.setBoardNum(boardNum);
		board.setText(text);
		board.setTitle(title);
		
		// 시간도 갱신해야 할까
		
		boardMapper.updateBoard(board);
		
	}
	
	/**
	 * 	페이징
	 */
	public Page<Board> getList(int page){
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("writeDateTime"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return boardRepository.findAll(pageable);
	}
	
}
