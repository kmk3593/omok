package com.osite.omok.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.HistoryCommentDTO;
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
	
	private final BoardService boardService;
	private final UserService userService;
	
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
