package com.messaging.services.chat;

import java.util.Optional;


public interface ChatRoomService {

    Optional<String> getChatId(String senderUsername, String recipientUsername, boolean createIfNotExist);
}
