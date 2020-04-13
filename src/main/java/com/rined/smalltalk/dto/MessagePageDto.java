package com.rined.smalltalk.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private final List<MessageDto> messages;
    private final int currentPage;
    private final int totalPages;
}
