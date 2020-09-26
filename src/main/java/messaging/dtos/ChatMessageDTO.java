package messaging.dtos;

import lombok.*;
import messaging.models.chat.MessageStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDTO {

    @NonNull private Long senderId;
    @NonNull private Long recipientId;
    private String content;

    @Builder.Default
    private Date timestamp = new Date();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MessageStatus status = MessageStatus.RECEIVED;
}
