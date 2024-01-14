package com.osite.omok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@GetMapping("hello")
	@ResponseBody  /* 객체 반환.  이거 없으면 return에 적힌 html 페이지를 찾아감 */
	public String hello() {
		return "HELLO WORLD 7777";
	}
	
	@GetMapping("/")
	public String main() {
		return "main_form";
	}
	
	@GetMapping("/test")
	public String testPage() {
		return "test";
	}
	
}
