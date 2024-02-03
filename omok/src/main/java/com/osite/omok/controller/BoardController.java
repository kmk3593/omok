package com.osite.omok.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.HistoryCommentDTO;
import com.osite.omok.entity.Board;
import com.osite.omok.entity.HistoryComment;
import com.osite.omok.entity.OmokOrder;
import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;
import com.osite.omok.service.BoardService;
import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	
	@Autowired
	private BoardService boardService;
	
	private final UserService userService;
	
	/**
	 * 	게시글 목록 전체 조회 
	*/
//	@GetMapping("/board")
//	public String getAllBoardList(Model model) {
//		
//		List<Board> boardList = boardRepository.findAll();
//		model.addAttribute("boardList", boardList);
//		
//		return "board_form";
//		
//	}

	/**
	 *  게시글 목록.
	 * 	그런데 게시글 페이징을 추가한. 
	*/
	@GetMapping("/board")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="search", defaultValue="") String search) {
		
		System.out.println("fffffffff = " + search);
		
		Page<Board> paging = search.isEmpty() ? boardService.getList(page) : boardService.getSearchList(page, search);
		
		model.addAttribute("boardList", paging);
		return "board_form";
		
	}
	
	
	/**
	 * 게시글 작성 페이지로 이동
	*/
	@GetMapping("/board/create")
	public String createPage() {
		
		return "board_create";
		
	}
	
	/**
	 *	게시글 작성하기 
	*/
	@PostMapping("/board/create")
	public String createBoard(Board board, Principal principal) {
		
		boardService.boardCreate(board.getTitle(), board.getText(), principal);
		
		return "redirect:/board";
	}
	
	/**
	 * 	게시글 상세보기
	*/
	@GetMapping("/board/detail/{boardNum}")
	public String boardDetailView(Model model, @PathVariable("boardNum") Integer boardNum) {
		
		Board board = boardService.getBoardDetail(boardNum);
		model.addAttribute("viewDetail", board);
		
		return "board_detail";
	}
	
	/**
	 * 	게시글 삭제하기
	 *  board/{boardNum}/delete 로 바꿀 예정
	*/
//	@DeleteMapping("/board/delete/{boardNum}")
	@GetMapping("/board/delete/{boardNum}")
	public String deleteBoard(@PathVariable("boardNum") Integer boardNum) {
		
		boardService.deleteBoard(boardNum);
		
		return "redirect:/board";
//		return null;
	}
	
	/**
	 * 	게시글 수정 페이지로 이동
	*/
	@GetMapping("/board/updating/{boardNum}")
	public String boardUpdatePage(Model model, @PathVariable("boardNum") Integer boardNum) {
		
		Board board = boardService.getBoardDetail(boardNum);
		model.addAttribute("viewDetail", board);
		
		return "board_update";
	}
	
	
	/**
	 * 	게시글 수정하기
	 */
	@GetMapping("/board/update/{boardNum}")
	public String boardUpdate(Board board, @PathVariable("boardNum") Integer boardNum) {
		
		Integer number = board.getBoardNum();
		System.out.println("UUUUUUUUUUUUUUUUUUUUU => " + board.toString());
		boardService.updateBoard(board.getBoardNum() , board.getTitle(), board.getText());
		
		return "redirect:/board/detail/" + number;
	}
	
	@GetMapping("/board/history/list")
	public String getOmokHistoryList(Model model) {
		List<OmokSetting> omokHistory = boardService.getOmokHistoryList();
		model.addAttribute("historis", omokHistory);
		return "OmokHistoryList";
	}
	
	@GetMapping(value = "/board/history/{gameNum}")
	public String enterOmokHistoryPage(Model model
			, @PathVariable(name = "gameNum") Integer gameNum
			, Authentication authentication) {
		
		List<OmokOrder> orders = boardService.getOmokOrders(gameNum);
		model.addAttribute("orders", orders);
		
		OmokSetting setting = boardService.getOmokSetting(gameNum);
		model.addAttribute("setting", setting);
		
		List<HistoryComment> historyComments = boardService.getHistoryComment(gameNum);
		model.addAttribute("comments", historyComments);
		
		if (authentication != null) {
			UserTable user = userService.getUser(authentication.getName());
			model.addAttribute("user", user);
		}
		
		return "OmokHistoryPage";
	}
	
	@PostMapping("/save/history/comment")
	@ResponseBody
	public boolean saveHistoryComment(HistoryCommentDTO historyCommentDTO) { 
		System.out.println(historyCommentDTO);
		System.out.println(historyCommentDTO.getGameNumGameNum());
		System.out.println(historyCommentDTO.getUserNumUserNum());
		return boardService.saveHistoryComment(historyCommentDTO);
	}

	@PostMapping("/delete/history/comment")
	@ResponseBody
	public int deleteHistoryComment(HistoryCommentDTO historyCommentDTO) {
		System.out.println(historyCommentDTO);
		return boardService.deleteHistoryComment(historyCommentDTO);
	}
	
	@PostMapping("/update/history/comment")
	@ResponseBody
	public int updateHistoryComment(HistoryCommentDTO historyCommentDTO) {
		System.out.println(historyCommentDTO);
		return boardService.updateHistoryComment(historyCommentDTO);
	}
	
}
