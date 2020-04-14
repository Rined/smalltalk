package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.MessageDto;
import com.rined.smalltalk.dto.MessagePageDto;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    public static final int MESSAGES_PER_PAGE = 3;
    private final MessageService messageService;

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = MESSAGES_PER_PAGE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public MessageDto getOne(@PathVariable("id") Long id) {
        return messageService.findById(id);
    }

    @PostMapping
    public MessageDto create(@RequestBody Message message,
                             @AuthenticationPrincipal User user) throws IOException {
        return messageService.save(message, user);
    }

    @PutMapping("{id}")
    public MessageDto update(@PathVariable("id") Message messageFromDb,
                             @RequestBody Message message) throws IOException {
        return messageService.updateMessage(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Message message) {
        messageService.deleteById(message);
    }
}