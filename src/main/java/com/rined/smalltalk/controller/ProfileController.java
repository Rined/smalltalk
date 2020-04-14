package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber,
                                   @PathVariable("channelId") User channel) {
        if (subscriber.equals(channel))
            return channel;
        return profileService.changeSubscription(channel, subscriber);
    }

}
