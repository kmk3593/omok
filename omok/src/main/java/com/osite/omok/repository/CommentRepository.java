package com.osite.omok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
