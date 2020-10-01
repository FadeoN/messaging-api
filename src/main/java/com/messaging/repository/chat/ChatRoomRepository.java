package com.messaging.repository.chat;

import com.messaging.models.chat.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

    @Query("SELECT cr From ChatRoom cr WHERE (cr.senderUsername = ?1 and cr.recipientUsername = ?2) or (cr.senderUsername = ?2 and cr.recipientUsername = ?1)")
    Optional<ChatRoom> findBySenderUsernameAndRecipientUsername(String senderUsername, String recipientUsername);
}
