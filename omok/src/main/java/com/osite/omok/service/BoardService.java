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
import com.osite.omok.dto.HistoryCommentDTO;
import com.osite.omok.dto.OrderDTO;
import com.osite.omok.dto.SettingDTO;
import com.osite.omok.entity.Board;
import com.osite.omok.entity.HistoryComment;
import com.osite.omok.entity.OmokOrder;
import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;
import com.osite.omok.mapper.BoardMapper;
import com.osite.omok.mapper.HistoryCommentMapper;
import com.osite.omok.mapper.OmokOrderMapper;
import com.osite.omok.mapper.OmokSettingMapper;
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
	private final OmokSettingMapper omokSettingMapper;
	private final OmokOrderMapper omokOrderMapper;
	private final HistoryCommentMapper historyCommentMapper;
	
	public List<OmokSetting> getOmokHistoryList() {
		
		List<SettingDTO> omokEndGameList = omokSettingMapper.getAllOmokHistoryList();
		
		List<OmokSetting> omokSettings = new ArrayList<>();
		
		for (SettingDTO endGame : omokEndGameList) {
			OmokSetting setting = settingDtoToOmokSetting(endGame);;
			omokSettings.add(setting);
		}
		
		return omokSettings;
	}
	
	public List<OmokOrder> getOmokOrders(Integer gameNum){
		
		List<OrderDTO> orderDtos = omokOrderMapper.getOmokOrdersFromGameNum(gameNum);
		List<OmokOrder> orders = new ArrayList<>();
		
		for (OrderDTO order : orderDtos) {
			OmokOrder omokOrder = order;
			Integer placedUserNum = order.getUserNumUserNum();
			UserTable placedUser = userTableMapper.findByUserNum(placedUserNum);
			
			omokOrder.setUserNum(placedUser);
			orders.add(omokOrder);
		}
		
		System.out.println(orders);
		
		return orders;
	}
	
	public OmokSetting getOmokSetting(Integer gameNum) {
		// TODO Auto-generated method stub
		SettingDTO settingDTO = omokSettingMapper.findOmokSetting(gameNum);
		OmokSetting omokSetting = settingDtoToOmokSetting(settingDTO);
		return omokSetting;
	}
	
	public List<HistoryComment> getHistoryComment(Integer gameNum) {
		// TODO Auto-generated method stub
		List<HistoryComment> historyComments = new ArrayList<>();
		List<HistoryCommentDTO> commentDtoList = new ArrayList<>();

		try {
			commentDtoList = historyCommentMapper.getHistoryCommentList(gameNum);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("댓글 : null");
			
		}
		
		for (HistoryCommentDTO CommentDTO : commentDtoList) {
			HistoryComment comment = CommentDTO;
			
			UserTable user = userTableMapper.findByUserNum(CommentDTO.getUserNumUserNum());
			comment.setUserNum(user);
			
			historyComments.add(comment);
		}
		
		return historyComments;
	}
	
	/**
	 * input SettingDTO
	 * set (black, white, winner, loser)
	 * output OmokSetting
	 * by Woobin
	**/
	public OmokSetting settingDtoToOmokSetting(SettingDTO settingDTO) {
			
		OmokSetting setting = settingDTO;
			
		Integer black = settingDTO.getBlackStoneUserNum();
		Integer white = settingDTO.getBlackStoneUserNum();
		Integer winner = settingDTO.getWinnerUserNum();
		Integer loser = settingDTO.getLoserUserNum();
		
		setting.setBlackStone(userTableMapper.findByUserNum(black));
		setting.setWhiteStone(userTableMapper.findByUserNum(white));
		setting.setWinner(userTableMapper.findByUserNum(winner));
		setting.setLoser(userTableMapper.findByUserNum(loser));
		
		return setting;
	}
	
	/**
	 * input HistoryCommentDTO
	 * set (userNum, gameNum)
	 * output HistoryComment
	 * by Woobin
	**/
	public HistoryComment historyCommentDTOToHistoryComment(HistoryCommentDTO historyCommentDTO) {
			
		HistoryComment historyComment = historyCommentDTO;
			
		UserTable user = userTableMapper.findByUserNum(historyCommentDTO.getUserNumUserNum());
		OmokSetting setting = omokSettingMapper.findOmokSetting(historyCommentDTO.getGameNumGameNum());
		
		historyComment.setUserNum(user);
		historyComment.setGameNum(setting);
		
		return historyComment;
	}
	
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

	public boolean saveHistoryComment(HistoryCommentDTO historyCommentDTO) {
		// TODO Auto-generated method stub
		HistoryComment historyComment = historyCommentDTOToHistoryComment(historyCommentDTO);
		System.out.println("매퍼 직전");
		System.out.println(historyComment);
		int result = historyCommentMapper.saveComment(historyComment);
		System.out.println(result);
		System.out.println("매퍼 직후");
		
		return false;
	}

	public int deleteHistoryComment(HistoryCommentDTO historyCommentDTO) {
		// TODO Auto-generated method stub
		HistoryComment historyComment = historyCommentDTO;
		
		int result = historyCommentMapper.deleteComment(historyComment);
		return result;
	}

	public int updateHistoryComment(HistoryCommentDTO historyCommentDTO) {
		// TODO Auto-generated method stub
		HistoryComment historyComment = historyCommentDTO;
		
		historyCommentDTOToHistoryComment(historyCommentDTO);
		
		int result = historyCommentMapper.updateComment(historyComment);
		
		return result;
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
