package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.*;
import com.rined.smalltalk.dto.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
@EqualsAndHashCode(of = {"id"})
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @JsonView(Views.IdName.class)
    private String id;

    @Column(name = "name")
    @JsonView(Views.IdName.class)
    private String name;

    @Column(name = "userpic")
    @JsonView(Views.IdName.class)
    private String picture;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @JsonView(Views.FullProfile.class)
    private String gender;

    @Column(name = "locale")
    @JsonView(Views.FullProfile.class)
    private String locale;

    @Column(name = "last_visit")
    @JsonView(Views.FullProfile.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

    @JsonView(Views.FullProfile.class)
    // все пользователи будут отображены только как id
    @JsonIdentityReference
    // если при сериализации один класс встречается более двух раз, то все вхождения со второго заменяются
    // на идентификатор объекта
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<User> subscriptions = new HashSet<>();

    @JsonIdentityReference
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    @JsonView(Views.FullProfile.class)
    private Set<User> subscribers = new HashSet<>();

}
