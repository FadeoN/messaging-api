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
                .senderUsername(entity.getSenderUsername())
                .recipientUsername(entity.getRecipientUsername())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public ChatMessage toEntity(ChatMessageDTO dto) {

        return ChatMessage.builder()
                .senderUsername(dto.getSenderUsername())
                .recipientUsername(dto.getRecipientUsername())
                .content(dto.getContent())
                .timestamp(dto.getTimestamp())
                .status(dto.getStatus())
                .build();
    }
}
