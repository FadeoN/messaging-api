package messaging.services.chat;

import messaging.dtos.ChatMessageDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.chat.MessageStatus;
import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    ChatMessage save(ChatMessageDTO user);
    Optional<ChatMessage> findById(String id);
    List<ChatMessage> findChatMessages(String senderUsername, String recipientUsername);
    Long countNewMessages(String senderUsername, String recipientUsername);

    void updateStatuses(String senderUsername, String recipientUsername, MessageStatus status);
}
