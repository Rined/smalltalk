package com.rined.smalltalk.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(Views.Id.class)
public class WsEvent {
    private ObjectType objectType;

    private EventType eventType;

    @JsonRawValue
    private String body;
}
