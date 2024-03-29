package com.osite.omok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.UserTable;

public interface UserTableRepository extends JpaRepository<UserTable, Integer>{

	Optional<UserTable> findByusername(String username); 
	
	UserTable findBynickname(String nickname);
	
	List<UserTable> findByuserNumBetween(Integer StartNum, Integer EndNum);
	
	UserTable findByuserNum(Integer userNum);
	
}
