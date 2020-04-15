package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.domain.UserSubscription;
import com.rined.smalltalk.dto.*;
import com.rined.smalltalk.exceptions.NotFoundException;
import com.rined.smalltalk.mapper.MessageMapper;
import com.rined.smalltalk.repositories.MessageRepository;
import com.rined.smalltalk.repositories.UserSubscriptionRepository;
import com.rined.smalltalk.ws.WsSender;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;
    private final UserSubscriptionRepository userSubscriptionRepository;

    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMG_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMG_PATTERN, Pattern.CASE_INSENSITIVE);

    private final BiConsumer<EventType, MessageDto> wsSender;

    @Autowired
    public MessageServiceImpl(MessageRepository repository,
                              MessageMapper mapper,
                              WsSender wsSender,
                              UserSubscriptionRepository userSubscriptionRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());
        channels.add(user);

        Page<Message> page = repository.findByAuthorIn(channels, pageable);
        return new MessagePageDto(
                page.stream().map(mapper::toDto).collect(Collectors.toList()),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    @Override
    public MessageDto save(Message message, User user) throws IOException {
        fillMeta(message);
        message.setCreationDate(LocalDateTime.now());
        message.setAuthor(user);
        MessageDto createdMessage = mapper.toDto(repository.save(message));
        wsSender.accept(EventType.CREATE, createdMessage);
        return createdMessage;
    }

    @Override
    public MessageDto updateMessage(Message messageFromDb, Message message) throws IOException {
        fillMeta(messageFromDb);
        messageFromDb.setText(message.getText());
        MessageDto updatedMessage = mapper.toDto(repository.save(messageFromDb));
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @Override
    public void deleteById(Message message) {
        repository.deleteById(message.getId());
        MessageDto deletedMessage = mapper.toDto(message);
        wsSender.accept(EventType.DELETE, deletedMessage);
    }

    @Override
    public MessageDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Message with id %d not found!", id));
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

    private String getContent(Element element) {
        return Objects.isNull(element) ? "" : element.attr("content");
    }
}
