package com.rined.smalltalk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.*;
import com.rined.smalltalk.services.MessageService;
import com.rined.smalltalk.ws.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("message")
public class MessageController {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMG_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMG_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageService messageService;
    private final BiConsumer<EventType, MessageDto> wsSender;

    public MessageController(MessageService messageService, WsSender wsSender) {
        this.messageService = messageService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<MessageDto> list() {
        return messageService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public MessageDto getOne(@PathVariable("id") Long id) {
        return messageService.findById(id);
    }

    @PostMapping
    public MessageDto create(@RequestBody Message message) throws IOException {
        fillMeta(message);
        message.setCreationDate(LocalDateTime.now());
        MessageDto createdMessage = messageService.save(message);
        wsSender.accept(EventType.CREATE, createdMessage);
        return createdMessage;
    }

    @PutMapping("{id}")
    public MessageDto update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {
        fillMeta(messageFromDb);
        MessageDto updatedMessage = messageService.updateMessage(messageFromDb, message);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Message message) {
        MessageDto deletedMessage = messageService.deleteById(message);
        wsSender.accept(EventType.DELETE, deletedMessage);
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);
        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            matcher = IMG_REGEX.matcher(url);
            message.setLink(url);
            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);
                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title], meta[property$=title]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");

        return new MetaDto(
               getContent(title.first()),
               getContent(description.first()),
               getContent(cover.first())
        );
    }

    private String getContent(Element element){
        return Objects.isNull(element) ? "" : element.attr("content");
    }

//    @SendTo("/topic/activity")
//    @MessageMapping("/changeMessage")
//    public MessageDto change(Message message) {
//        return messageService.save(message);
//    }
}