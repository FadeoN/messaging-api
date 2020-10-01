package com.messaging.services.chat;

import com.messaging.dtos.ChatMessageDTO;
import com.messaging.models.chat.ChatMessage;
import com.messaging.models.chat.MessageStatus;
import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    ChatMessage save(ChatMessageDTO user);
    Optional<ChatMessage> findById(String id);
    List<ChatMessage> findChatMessages(String senderUsername, String recipientUsername);
    Long countNewMessages(String senderUsername, String recipientUsername);

    void updateStatuses(String senderUsername, String recipientUsername, MessageStatus status);
}
