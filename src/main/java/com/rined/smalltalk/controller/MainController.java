package com.rined.smalltalk.controller;

import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if (Objects.nonNull(user)) {
            data.put("profile", user);
            data.put("messages", messageService.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

}
