package messaging.services.chat;

import messaging.converters.ChatMessageMapper;
import messaging.dtos.ChatMessageDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.chat.MessageStatus;
import messaging.repository.chat.ChatMessageRepository;
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


    public ChatMessage save(ChatMessageDTO chatMessageDTO) {
        String chatId = chatRoomService
                .getChatId(chatMessageDTO.getSenderId(), chatMessageDTO.getRecipientId(), true).get();
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
        chatMessage.setChatId(chatId);
        chatMessage.setStatus(MessageStatus.RECEIVED);
        ChatMessage saved = chatMessageRepository.save(chatMessage);

        return saved;
    }

    public Optional<ChatMessage> findById(String id){
        return chatMessageRepository.findById(id);
    }

    @Override
    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        System.out.println(senderId);
        System.out.println(recipientId);
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        List<ChatMessage> messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    public Long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    @Override
    public void updateStatuses(Long senderId, Long recipientId, MessageStatus status) {
        chatMessageRepository.updateStatuses(senderId, recipientId, status);
    }
}
