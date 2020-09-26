package messaging.converters;

import messaging.dtos.ChatMessageDTO;
import messaging.dtos.UserDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.user.User;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageMapperImpl implements ChatMessageMapper{

    @Override
    public ChatMessageDTO toDTO(ChatMessage entity) {
        return ChatMessageDTO.builder()
                .senderId(entity.getSenderId())
                .recipientId(entity.getRecipientId())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public ChatMessage toEntity(ChatMessageDTO dto) {

        return ChatMessage.builder()
                .senderId(dto.getSenderId())
                .recipientId(dto.getRecipientId())
                .content(dto.getContent())
                .timestamp(dto.getTimestamp())
                .status(dto.getStatus())
                .build();
    }
}
