package messaging.converters;

import messaging.dtos.ChatMessageDTO;
import messaging.dtos.UserDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.user.User;

public interface ChatMessageMapper {

    ChatMessageDTO toDTO(ChatMessage entity);

    ChatMessage toEntity(ChatMessageDTO dto);
}
