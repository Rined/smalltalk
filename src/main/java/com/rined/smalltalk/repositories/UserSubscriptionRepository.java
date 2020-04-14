package com.rined.smalltalk.repositories;

import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.domain.UserSubscription;
import com.rined.smalltalk.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

    List<UserSubscription> findBySubscriber(User user);

}
