package com.osite.omok.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.OmokRoomDTO;
import com.osite.omok.dto.TransferMessage;
import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;
import com.osite.omok.service.OmokService;
import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OmokController {
	
	private final OmokService omokService;
	private final UserService userService;
	
	@GetMapping("/omok/list")
	public String omokList(Model model) {
		List<OmokSetting> roomList = omokService.findAbleToEnterOmokRoom();
		model.addAttribute("roomList", roomList);
		
		return "OmokList";
	}
	
	@PostMapping("/omok/create/room")  //방을 만들었으면 해당 방으로 가야지.
    public String createRoom(Model model
    		, OmokSetting omokSetting
    		, Authentication authentication
    		) {
		
//		String 유저정보 = authentication.getPrincipal().toString();
//		String 아이디 = authentication.getName();
//		System.out.println(아이디);
		
		// 방 이름, 만든 유저 아이디를 전송
		omokSetting = omokService.createRoom(omokSetting, authentication.getName());

		String uuid = omokSetting.getRoomID(); 
		
        return "redirect:/omok/omokroom/"+uuid;  //만든사람이 오목방에 첫번째로 입장
    }
	
	@GetMapping(value = "/omok/omokroom/{roomID}")
	public String enterOmokRoom(Model model
			, @PathVariable(name = "roomID") String roomID
			, Authentication authentication) {
		
		
		// false는 입장 불허, true는 입장 허용
//		boolean enter = true;
		
		String stone = "";
		
		OmokRoomDTO room = omokService.findRoomByID(roomID);
		
		// 해당 roomID로 생성되어있는 방이 없다면 메인화면으로 유도
		if (room.equals(null)) {
			return "redirect:/";
		}
		
		// 방 id를 room이란 이름으로 넣어서 전송
		model.addAttribute("room", room);

		// 유저 인식번호를 userNum으로 실어서 전송
		String 아이디 = authentication.getName();
		UserTable user = userService.getUserInfo(아이디);
		model.addAttribute("userNum", user.getUserNum());
		model.addAttribute("user", user);
		
		stone = omokService.enterOmokPlayer(roomID, authentication);
		
		if (stone==null) {
			return "redirect:/";
		}
		model.addAttribute("stone", stone);
		return "OmokBoardPage";
	}
	
	
	// ajax
	@GetMapping(value = "/omok/player/check")
	@ResponseBody
	public int playerNumberCheck(TransferMessage transferMessage) {
		OmokRoomDTO omokRoom = omokService.findRoomByID(transferMessage.getRoomID());
		
		return omokRoom.getSessions().size();
	}
	
}
