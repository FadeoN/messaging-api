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

    Long countBySenderUsernameAndRecipientUsernameAndStatus(String senderUsername, String recipientUsername, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage cm SET cm.status = ?3 WHERE cm.senderUsername = ?1 AND cm.recipientUsername = ?2")
    void updateStatuses(String senderUsername, String recipientUsername, MessageStatus status);

}
