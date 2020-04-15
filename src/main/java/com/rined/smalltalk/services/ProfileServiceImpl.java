package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.domain.UserSubscription;
import com.rined.smalltalk.repositories.UserDetailsRepository;
import com.rined.smalltalk.repositories.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserDetailsRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> userSubscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if (userSubscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(userSubscriptions);
        }
        return userRepository.save(channel);
    }

    @Override
    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    @Override
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription userSubscription = userSubscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        userSubscription.setActive(!userSubscription.isActive());
        return userSubscriptionRepository.save(userSubscription);
    }
}
