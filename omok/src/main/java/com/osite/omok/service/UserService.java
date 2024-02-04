package com.osite.omok.service;

import java.util.Optional;

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
		user.setWinLate(0);
		user.setLoseLate(0);
		
		// password는 암호화하여 저장. 
		user.setPassword(passwordEncoder.encode(password));
		
		this.userRepository.save(user);
	
		return user;
	}
	
	public UserTable getUserInfo(String username) {
		Optional<UserTable> optionalUser = userRepository.findByusername(username);
		UserTable user = optionalUser.get();
		return user;
	}
	
	
	public UserTable getUser(String username) {
		
		Optional<UserTable> _userTable = userRepository.findByusername(username);
		
		if(_userTable.isEmpty()) {
			return null;
			
		}else {
			UserTable userTable = _userTable.get();
			return userTable;
		}
		
	}

	public String getNicknameByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<UserTable> optionalUser = userRepository.findByusername(username);
		UserTable user = optionalUser.get();
		
		return user.getNickname();
	}
	
}
