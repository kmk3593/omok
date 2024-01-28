package com.osite.omok.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.UserTableRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	private final UserTableRepository userTableRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		User user = (User) authentication.getPrincipal();

 
		LocalDateTime nowTime = LocalDateTime.now(); 
		
		Optional<UserTable> loginUser = userTableRepository.findByusername(user.getUsername());
		
		UserTable _userTable = loginUser.get();
		
		_userTable.setLastLoginDate(nowTime);
		
		userTableRepository.save(_userTable);
		
		response.sendRedirect("/");
	}
	
}
