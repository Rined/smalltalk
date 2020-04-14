package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.domain.User;
import com.rined.smalltalk.dto.MessageDto;
import com.rined.smalltalk.dto.MessagePageDto;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface MessageService {

    MessagePageDto findForUser(Pageable pageable, User user);

    MessageDto save(Message message, User user) throws IOException;

    MessageDto updateMessage(Message messageFromDb, Message message) throws IOException;

    void deleteById(Message message);

    MessageDto findById(Long id);

}
