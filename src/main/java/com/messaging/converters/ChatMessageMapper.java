package com.messaging.converters;

import com.messaging.dtos.ChatMessageDTO;
import com.messaging.models.chat.ChatMessage;

public interface ChatMessageMapper {

    ChatMessageDTO toDTO(ChatMessage entity);

    ChatMessage toEntity(ChatMessageDTO dto);
}
