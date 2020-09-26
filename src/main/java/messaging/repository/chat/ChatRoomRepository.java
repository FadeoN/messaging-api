package messaging.repository.chat;

import messaging.models.chat.ChatRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

    Optional<ChatRoom> findBySenderUsernameAndRecipientUsername(String senderUsername, String recipientUsername);
}
