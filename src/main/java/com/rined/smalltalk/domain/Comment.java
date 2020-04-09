package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.dto.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment")
@EqualsAndHashCode(of = {"id"})
public class Comment {

    @Id
    @GeneratedValue
    @JsonView(Views.IdName.class)
    private long id;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.IdName.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User author;

}
