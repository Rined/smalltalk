package com.rined.smalltalk.mapper;

import com.rined.smalltalk.domain.Message;
import com.rined.smalltalk.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mappings({
            @Mapping(target = "id", source = "message.id"),
            @Mapping(target = "text", source = "message.text"),
            @Mapping(target = "creationDate", source = "message.creationDate")
    })
    MessageDto toDto(Message message);

}
