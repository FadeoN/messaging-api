package messaging.services.chat;

import messaging.dtos.ChatMessageDTO;
import messaging.dtos.UserDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.chat.MessageStatus;
import messaging.models.user.User;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    ChatMessage save(ChatMessageDTO user);
    Optional<ChatMessage> findById(String id);
    List<ChatMessage> findChatMessages(Long senderId, Long recipientId);
    Long countNewMessages(Long senderId, Long recipientId);

    void updateStatuses(Long senderId, Long recipientId, MessageStatus status);
}
