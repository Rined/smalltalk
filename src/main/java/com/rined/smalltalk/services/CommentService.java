package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Comment;
import com.rined.smalltalk.domain.User;

public interface CommentService {

    Comment create(Comment comment, User user);

}
