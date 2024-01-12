package com.osite.omok.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter

public class TestDTO {
	
	private final int a;
	private final long b;
	private final String c;
	private final boolean d;
	//  final이 없는 속성은 생성자에 포함되지 않는다 
	
	private static void main(String[] args) {
		TestDTO testDTO = new TestDTO(5, 10, "boss", true);
		System.out.println(testDTO.getA());
	}
	
}
