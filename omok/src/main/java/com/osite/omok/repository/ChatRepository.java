package com.osite.omok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.Chat;
import com.osite.omok.entity.UserTable;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
	
	Chat findByuserNum(UserTable userNum);

}
