package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserDetailsRepository userRepository;

    @Override
    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();
        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }
        return userRepository.save(channel);
    }
}
