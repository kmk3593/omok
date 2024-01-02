package com.osite.omok.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.UserTable;

public interface UserTableRepository extends JpaRepository<UserTable, Integer>{

	Optional<UserTable> findByusername(String username);
	
}
