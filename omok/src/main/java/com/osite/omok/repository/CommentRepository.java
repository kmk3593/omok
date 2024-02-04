package com.osite.omok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findByboardNumBoardNum(Integer boardNum);
	
	Comment findByCommentNum(Integer commentNum); 
}
