package com.osite.omok.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class OmokController {

	@GetMapping("omok")
	public String omokPage() {
//		return "OmokBoardPage";
		return "test";
	}
	
}
