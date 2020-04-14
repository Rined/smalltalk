package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.User;

public interface ProfileService {

    User changeSubscription(User channel, User subscriber);

}
