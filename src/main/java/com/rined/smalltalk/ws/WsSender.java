package com.rined.smalltalk.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rined.smalltalk.dto.EventType;
import com.rined.smalltalk.dto.ObjectType;
import com.rined.smalltalk.dto.Views;
import com.rined.smalltalk.dto.WsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class WsSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class<? extends Views.View> view) {
        ObjectWriter writer = mapper.writerWithView(view);
        return (EventType eventType, T payload) -> {
            String value;
            try {
                value = writer.writeValueAsString(payload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            template.convertAndSend(
                    "/topic/activity",
                    new WsEvent(objectType, eventType, value)
            );
        };
    }
}
