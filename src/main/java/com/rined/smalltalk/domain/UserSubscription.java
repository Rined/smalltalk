package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.*;
import com.rined.smalltalk.dto.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(of = "id")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "user_subscription")
public class UserSubscription {

    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @ManyToOne
    @MapsId("channelId")
    @JsonView(Views.IdName.class)
    @JsonIdentityReference  // все пользователи будут отображены только как id
    // если при сериализации один класс встречается более двух раз, то все вхождения со второго заменяются
    // на идентификатор объекта
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    private User channel;

    @ManyToOne
    @MapsId("subscriberId")
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    private User subscriber;

    @JsonView(Views.IdName.class)
    private boolean active;

    public UserSubscription(User channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }
}
