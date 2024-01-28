package com.osite.omok.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.BoardRepository;
import com.osite.omok.repository.UserTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final UserTableRepository userTableRepository;
	
	/**
	 * 게시글 작성 
	 * 
	**/
	public void boardCreate(String title, String text, Principal principal) {
		Board board = new Board();
		board.setTitle(title);
		board.setText(text);
		
		// principal을 통해 로그인된 id를 획득. id를 통해 테이블 객체를 얻어 board에 set.
		Optional<UserTable> _userTable = userTableRepository.findByusername(principal.getName());
		UserTable userTable = _userTable.get();
		board.setWriter(userTable);
		
		// 현재 시간 저장
		LocalDateTime createTime = LocalDateTime.now();
		board.setWriteDateTime(createTime);
		
		// 게시글 내용을 create
		boardRepository.save(board);
		
	}
}
