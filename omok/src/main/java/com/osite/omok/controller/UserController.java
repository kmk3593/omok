package com.osite.omok.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osite.omok.dto.UserCreateForm;
import com.osite.omok.entity.UserTable;
import com.osite.omok.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user") // request = post + get
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm, Principal principal) {
		if (principal != null) {
			return "/";
		}
		return "signup_form"; 
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getNickname());
		
			return "redirect:/";		
	}
	
	@GetMapping("/login")
	public String login(Principal principal) {
		if (principal != null) {
			return "/";
		}
		return "login_form";
	}
	
	/**
	 * 회원 가입 시, ID 중복 체크
	 * 
	**/	
	@PostMapping("/duplicate/check/id")
	@ResponseBody
	public Boolean sameCheck(UserTable userTable) {
		
		Boolean same = true; 
		String username = userTable.getUsername();
		
		if(userService.getUser(username) == null){
			System.out.println("=-===입력한 id로 검색한 유저정보 = " + userService.getUser(username));
			
		}else {
			same = false;
		}
		
		return same;
	}
	
	@PostMapping("/get/nickname")
	@ResponseBody
	public String getNickname(Principal principal) {
		
		if (principal == null) {
			return "";
		}
		String username = principal.getName(); // <- 아이디
		String nickname = userService.getNicknameByUsername(username);
		
		return nickname;
	}
}
