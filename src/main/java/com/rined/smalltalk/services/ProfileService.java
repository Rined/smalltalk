package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.domain.UserSubscription;

import java.util.List;

public interface ProfileService {

    User changeSubscription(User channel, User subscriber);

    List<UserSubscription> getSubscribers(User channel);

    UserSubscription changeSubscriptionStatus(User channel, User subscriber);
}
