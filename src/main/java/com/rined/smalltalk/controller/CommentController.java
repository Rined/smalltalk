package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Comment;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private final CommentService service;

    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Comment create(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        return service.create(comment, user);
    }

}
