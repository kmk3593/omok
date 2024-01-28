package com.osite.omok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	Board findByboardNum(Integer boardNum);
	
}
