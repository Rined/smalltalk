package com.rined.smalltalk.repositories;

import com.rined.smalltalk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}