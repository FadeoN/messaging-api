package messaging.models.chat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String chatId;
    private String senderUsername;
    private String recipientUsername;
    private String content;
    private Date timestamp;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

}
