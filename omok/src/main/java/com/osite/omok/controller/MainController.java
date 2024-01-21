package com.osite.omok.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@Autowired
	private UserService userService;
	
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
	

//	@GetMapping("hello")
//	@ResponseBody  /* 객체 반환.  이거 없으면 return에 적힌 html 페이지를 찾아감 */
//	public String hello() {
//		return "HELLO WORLD 7777";
//	}
	

	@GetMapping("/test")
	public String testPage() {
		return "test";
	}

	
}
