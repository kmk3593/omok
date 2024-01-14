package com.osite.omok.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.UserTableRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserTableRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	// create 사용 - user 데이터 생성
	public UserTable create(String username, String password, String nickname) {
		UserTable user = new UserTable();
		user.setUsername(username);
		user.setNickname(nickname);
		
		// password는 암호화하여 저장. 
		user.setPassword(passwordEncoder.encode(password));
		
		this.userRepository.save(user);
	
		return user;
	}
	
	public UserTable getUser(String username) {
		
		Optional<UserTable> _userTable = userRepository.findByusername(username);
		UserTable userTable = _userTable.get();

		System.out.println(userTable.toString());
		
		return userTable;
	}
	

}
