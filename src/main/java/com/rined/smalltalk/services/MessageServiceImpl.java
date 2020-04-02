package com.rined.smalltalk.services;

import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.MessageDto;
import com.rined.smalltalk.exceptions.NotFoundException;
import com.rined.smalltalk.mapper.MessageMapper;
import com.rined.smalltalk.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;

    @Override
    public List<MessageDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto save(Message message) {
        return mapper.toDto(repository.save(message));
    }

    @Override
    public MessageDto updateMessage(Message messageFromDb, Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return mapper.toDto(repository.save(messageFromDb));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public MessageDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Message with id %d not found!", id));
    }
}
