package messaging.services.chat;

import messaging.converters.ChatMessageMapper;
import messaging.dtos.ChatMessageDTO;
import messaging.dtos.UserDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.chat.MessageStatus;
import messaging.repository.chat.ChatMessageRepository;
import messaging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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


    public ChatMessage save(ChatMessageDTO chatMessageDTO) {
        String chatId = chatRoomService
                .getChatId(chatMessageDTO.getSenderUsername(), chatMessageDTO.getRecipientUsername(), true).get();
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
        chatMessage.setChatId(chatId);

        ChatMessage saved = chatMessageRepository.save(chatMessage);

        return saved;
    }

    public Optional<ChatMessage> findById(String id){
        return chatMessageRepository.findById(id);
    }

    @Override
    public List<ChatMessage> findChatMessages(String senderUsername, String recipientUsername) {

        Optional<String> chatId = chatRoomService.getChatId(senderUsername, recipientUsername, false);

        List<ChatMessage> messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderUsername, recipientUsername, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    public Long countNewMessages(String senderUsername, String recipientUsername) {
        return chatMessageRepository.countBySenderUsernameAndRecipientUsernameAndStatus(
                senderUsername, recipientUsername, MessageStatus.RECEIVED);
    }

    @Override
    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        chatMessageRepository.updateStatuses(senderId, recipientId, status);
    }
}
