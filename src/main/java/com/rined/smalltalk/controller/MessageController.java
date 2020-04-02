package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.MessageDto;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<MessageDto> list() {
        return messageService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public MessageDto getOne(@PathVariable("id") Long id) {
        return messageService.findById(id);
    }

    @PostMapping
    public MessageDto create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageService.save(message);
    }

    @PutMapping("{id}")
    public MessageDto update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        return messageService.updateMessage(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        messageService.deleteById(id);
    }
}
