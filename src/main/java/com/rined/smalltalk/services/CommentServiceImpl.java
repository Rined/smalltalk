package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Comment;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    @Override
    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        return repository.save(comment);
    }
}
