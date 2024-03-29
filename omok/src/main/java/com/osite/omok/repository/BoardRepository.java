package com.osite.omok.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	Board findByboardNum(Integer boardNum);
	
	Page<Board> findAll(Pageable pageable);
	
	/**
	 *  Title 컬럼으로 검색
	 * @param search
	 * @param pageable
	 * @return
	 */
	Page<Board> findByTitleContaining(String search, Pageable pageable);
	
}
 