package com.rined.smalltalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.time.LocalDateTime;

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

}
