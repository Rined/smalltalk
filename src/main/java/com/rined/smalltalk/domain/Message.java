package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.rined.smalltalk.dto.Views;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "message")
@Table(name = "message")
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Message {

    @Id
    @JsonView(Views.Id.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.IdName.class)
    @Column(name = "text")
    private String text;

    @JsonView(Views.FullMessage.class)
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @JsonView(Views.FullMessage.class)
    @Column(name = "link")
    private String link;

    @JsonView(Views.FullMessage.class)
    @Column(name = "link_title")
    private String linkTitle;

    @JsonView(Views.FullMessage.class)
    @Column(name = "link_description")
    private String linkDescription;

    @JsonView(Views.FullMessage.class)
    @Column(name = "link_cover")
    private String linkCover;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullMessage.class)
    private User author;

    @JsonView(Views.FullMessage.class)
    @OneToMany(mappedBy = "message", orphanRemoval = true)
    private List<Comment> comments;

}
