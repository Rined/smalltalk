package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.MessageDto;

import java.util.List;

public interface MessageService {

    List<MessageDto> findAll();

    MessageDto save(Message message);

    MessageDto updateMessage(Message messageFromDb, Message message);

    void deleteById(Long id);

    MessageDto findById(Long id);

}
