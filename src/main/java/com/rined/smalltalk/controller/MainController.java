package com.rined.smalltalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.MessagePageDto;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.repositories.UserDetailsRepository;
import com.rined.smalltalk.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;
    private final MessageService messageService;
    private final UserDetailsRepository userDetailsRepository;

    @Value("${spring.profiles.active:prod}")
    private String profile;

    @Autowired
    public MainController(MessageService messageService,
                          ObjectMapper objectMapper,
                          UserDetailsRepository userDetailsRepository) {
        this.messageService = messageService;
        this.userDetailsRepository = userDetailsRepository;
        this.messageWriter = objectMapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        if (Objects.nonNull(user)) {
            Optional<User> userProfile = userDetailsRepository.findById(user.getId());
            if(userProfile.isPresent()){
                String serializedProfile = profileWriter.writeValueAsString(userProfile.get());
                model.addAttribute("profile", serializedProfile);
            }

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageable = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findForUser(pageable, user);

            String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", messages);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

}
