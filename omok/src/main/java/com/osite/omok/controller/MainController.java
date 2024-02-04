package com.osite.omok.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.TransferMessage;
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
	

	@PostMapping("/test")
	@ResponseBody
	public String testPage(@RequestParam(value="str") String str) {
		
		System.out.println("dddddddddddd = " + str);
		
		
		return "ddddd";
	}
	
	@GetMapping("/test")
	public String test() {
		
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
