package com.rined.smalltalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final ObjectWriter writer;

    @Value("${spring.profiles.active}")
    private String profile;

    public MainController(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.writer = objectMapper.writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        if (Objects.nonNull(user)) {
            data.put("profile", user);
            model.addAttribute("messages", writer.writeValueAsString(messageService.findAll()));
        } else {
            model.addAttribute("messages", "[]");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

}
