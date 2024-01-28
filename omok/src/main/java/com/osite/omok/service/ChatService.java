package com.osite.omok.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.osite.omok.entity.Chat;
import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatService {
	
	private final ChatRepository chatRepository;
	
	public void chatSave(UserTable userNum, String message) {
		
		Chat chat = new Chat();
		chat.setUserNum(userNum);
		chat.setContent(message);
		
		LocalDateTime nowTime = LocalDateTime.now();
		chat.setDatetime(nowTime);
		
		chatRepository.save(chat);
	
	}

}