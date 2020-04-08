package com.rined.smalltalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Comment;
import com.rined.smalltalk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    @JsonProperty("id")
    @JsonView(Views.Id.class)
    private Long id;

    @JsonProperty("text")
    @JsonView(Views.IdName.class)
    private String text;

    @JsonProperty("creationDate")
    @JsonView(Views.FullMessage.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonProperty("author")
    @JsonView(Views.FullMessage.class)
    private User author;

    @JsonProperty("comments")
    @JsonView(Views.FullMessage.class)
    private List<Comment> comments;

    @JsonProperty("link")
    @JsonView(Views.FullMessage.class)
    private String link;

    @JsonProperty("linkTitle")
    @JsonView(Views.FullMessage.class)
    private String linkTitle;

    @JsonProperty("linkDescription")
    @JsonView(Views.FullMessage.class)
    private String linkDescription;

    @JsonProperty("linkCover")
    @JsonView(Views.FullMessage.class)
    private String linkCover;

}
