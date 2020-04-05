package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.EventType;
import com.rined.smalltalk.dto.MessageDto;
import com.rined.smalltalk.dto.ObjectType;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.MessageService;
import com.rined.smalltalk.ws.WsSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;
    private final BiConsumer<EventType, MessageDto> wsSender;

    public MessageController(MessageService messageService, WsSender wsSender) {
        this.messageService = messageService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

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
        MessageDto createdMessage = messageService.save(message);
        wsSender.accept(EventType.CREATE, createdMessage);
        return createdMessage;
    }

    @PutMapping("{id}")
    public MessageDto update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        MessageDto updatedMessage = messageService.updateMessage(messageFromDb, message);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Message message) {
        MessageDto deletedMessage = messageService.deleteById(message);
        wsSender.accept(EventType.DELETE, deletedMessage);
    }

//    @SendTo("/topic/activity")
//    @MessageMapping("/changeMessage")
//    public MessageDto change(Message message) {
//        return messageService.save(message);
//    }
}
