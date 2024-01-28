package com.osite.omok.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.TransferMessage;
import com.osite.omok.entity.Board;
import com.osite.omok.repository.BoardRepository;
import com.osite.omok.service.BoardService;
import com.osite.omok.service.TestService;
import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String main(Principal principal, Model model) {
		

		// 로그인 안 되있으면 principal이 비어있다
		if(principal!=null) { 
			
			
			// Principle = 스프링 시큐리티가 제공하는 객체. 로그인한 사용자의 정보를 가져온다.
			String name = principal.getName();
			System.out.println("name : " + name);
			
			model.addAttribute("User", userService.getUser(name));
//			model.toString();
			
			System.out.println(userService.getUser(name).toString());
		}

		return "main_form";
	}
	
	/**
	 * 		게시글 전체 조회 
	**/
	@GetMapping("/board")
	public String getAllBoardList(Model model) {
		
		List<Board> boardList = boardRepository.findAll();
		model.addAttribute("boardList", boardList);
		
		return "board_form";
		
	}
	
	/**
	 * 게시글 작성 페이지로 이동
	**/
	@GetMapping("/board/create")
	public String createPage() {
		
		return "board_create";
		
	}
	
	/**
	 *	게시글 작성하기 
	**/
	@PostMapping("/board/create")
	public String createBoard(Board board, Principal principal) {
		
		boardService.boardCreate(board.getTitle(), board.getText(), principal);
		
		return "redirect:/board";
	}
	
	/**
	 * 	게시글 상세보기
	**/
	@GetMapping("/board/detail/{boardNum}")
	public String boardDetailView(Model model, @PathVariable("boardNum") Integer boardNum) {
		
		// 게시글 목록에서 ㅎㅐ당 게시글 클릭시 그 게시글의 인덱스를 받아서 이동해야 한다.
		// 그 인덱스를 html에서 부여해야 되던가
		// 그 그 인덱스가 {boardNum}에 들어감
		// 그러면 해당하는 boardNum의 게시글 내용,제목 등을 가져와서 
		// boardDetail 페이지에 보여지게 한다.
		
		Board board = boardService.getBoardDetail(boardNum);
		model.addAttribute("viewDetail", board);
		
		return "boardDetail";
	}
	

	@GetMapping("/test")
	public String testPage(Principal principal) {
		try {
			String username = principal.getName();
			System.out.println(testService.testMapper(username));
		} catch (Exception e) {
			// TODO: handle exception
			testService.testMapper("aaa");
			System.out.println("catch");
		}
		return "test";
	}
	
	@PostMapping("/test/ajax")
	@ResponseBody
	public String ajaxTest(TransferMessage transferMessage) {
		System.out.println(transferMessage);
		testService.mapperInsert();
		testService.mapperSelect();
		return "good";
	}
	
	
}
