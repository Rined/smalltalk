package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.*;
import com.rined.smalltalk.dto.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
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
    @OneToMany(mappedBy = "subscriber", orphanRemoval = true)
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserSubscription> subscribers = new HashSet<>();

}
