package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Comment;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.EventType;
import com.rined.smalltalk.dto.ObjectType;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.CommentService;
import com.rined.smalltalk.ws.WsSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiConsumer;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService service;
    private final BiConsumer<EventType, Comment> wsSender;

    public CommentController(CommentService service, WsSender wsSender) {
        this.service = service;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        Comment createdComment = service.create(comment, user);
        wsSender.accept(EventType.CREATE, createdComment);
        return createdComment;
    }

}
