package com.rined.smalltalk.controller;

import com.rined.smalltalk.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "First message");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "Second message");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "Third message");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable("id") String id) {
        return getMessageById(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable("id") String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageById = getMessageById(id);
        messageById.putAll(message);
        messageById.put("id", id);
        return messageById;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String id) {
        Map<String, String> messageById = getMessageById(id);
        messages.remove(messageById);
    }

    private Map<String, String> getMessageById(@PathVariable("id") String id) {
        return messages.stream()
                .filter(msg -> msg.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

}
