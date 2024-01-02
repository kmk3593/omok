package com.osite.omok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
}
