package com.messaging.services.chat;

import com.messaging.converters.ChatMessageMapper;
import com.messaging.dtos.ChatMessageDTO;
import com.messaging.exceptions.RestBlockedByRecipientUserException;
import com.messaging.exceptions.RestRecipientDoesNotExistsException;
import com.messaging.exceptions.RestResourceNotFoundException;
import com.messaging.models.chat.ChatMessage;
import com.messaging.models.chat.MessageStatus;
import com.messaging.repository.chat.ChatMessageRepository;
import com.messaging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService{

    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private ChatRoomService chatRoomService;
    @Autowired private ChatMessageMapper chatMessageMapper;
    @Autowired private UserService userService;

    @Override
    public ChatMessage save(ChatMessageDTO chatMessageDTO) {
        if(userService.getByUsername(chatMessageDTO.getRecipientUsername()).isEmpty()) throw new RestRecipientDoesNotExistsException();
        if(userService.isBlockedByRecipientUser(chatMessageDTO.getSenderUsername(), chatMessageDTO.getRecipientUsername())) throw new RestBlockedByRecipientUserException();

        String chatId = chatRoomService
                .getChatId(chatMessageDTO.getSenderUsername(), chatMessageDTO.getRecipientUsername(), true).get();
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
        chatMessage.setChatId(chatId);


        ChatMessage saved = chatMessageRepository.save(chatMessage);

        return saved;
    }

    public Optional<ChatMessage> findById(String id){
        Optional<ChatMessage> chatMessage =  chatMessageRepository.findById(id);
        if(chatMessage.isEmpty()) throw new RestResourceNotFoundException();

        return chatMessage;
    }

    @Override
    public List<ChatMessage> findChatMessages(String loggedInUsername, String senderUsername) {

        Optional<String> chatId = chatRoomService.getChatId(loggedInUsername, senderUsername, false);
        List<ChatMessage> messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(!messages.isEmpty()) {
            updateStatuses(senderUsername, loggedInUsername, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    public Long countNewMessages(String senderUsername, String recipientUsername) {
        return chatMessageRepository.countBySenderUsernameAndRecipientUsernameAndStatus(
                senderUsername, recipientUsername, MessageStatus.RECEIVED);
    }

    @Override
    public void updateStatuses(String senderUsername, String recipientUsername, MessageStatus status) {
        chatMessageRepository.updateStatuses(senderUsername, recipientUsername, status);
    }
}
