package com.osite.omok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.UserTableRepository;

 
@SpringBootTest
class OmokApplicationTests {

	@Autowired
	private UserTableRepository userTableRepository;
	
	@Test
	void contextLoads() {
		
//		UserTable q = this.userTableRepository.findByNickname("name1");
//		assertEquals("id1", q.getUsername());
		
		System.out.println("==========================Hello World==========================");
	
		
	}
	 
	@Test 
	void getUser() {
		String nickname = "nick5";
		UserTable usertable = userTableRepository.findBynickname(nickname);
		System.out.println(usertable.toString());
		
	}
	
	@Test 
	void getUsernum() {
		Integer startNum = 3;
		Integer endNum = 5;
		List<UserTable> userTable2 = userTableRepository.findByuserNumBetween(startNum, endNum);
		System.out.println(userTable2.toString());
	}

}
