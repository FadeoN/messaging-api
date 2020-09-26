package messaging.repository.chat;

import messaging.models.chat.ChatMessage;
import messaging.models.chat.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

    Long countBySenderIdAndRecipientIdAndStatus(
            Long senderId, Long recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage cm SET cm.status = ?3 WHERE cm.senderId = ?1 AND cm.recipientId = ?2")
    void updateStatuses(Long senderId, Long recipientId, MessageStatus status);

}
