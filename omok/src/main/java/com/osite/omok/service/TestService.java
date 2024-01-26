package com.osite.omok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osite.omok.entity.UserTable;
import com.osite.omok.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	@Autowired
	private TestMapper testMapper;
	
	public List<UserTable> testMapper(String username) {
		List<UserTable> userTable = testMapper.testByID();
		System.out.println("mapper 테스트");
		System.out.println(userTable);
		return userTable;
	}
	
}
