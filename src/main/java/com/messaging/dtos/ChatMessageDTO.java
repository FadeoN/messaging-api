package com.messaging.dtos;

import lombok.*;
import com.messaging.models.chat.MessageStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@NoArgsConstructor
public class ChatMessageDTO {

    private String senderUsername;
    @NonNull private String recipientUsername;
    @NonNull private String content;

    private Date timestamp = new Date();

    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.RECEIVED;

    @Builder
    public ChatMessageDTO(@NonNull String recipientUsername, @NonNull String content) {
        this.recipientUsername = recipientUsername;
        this.content = content;
    }

    @Builder
    public ChatMessageDTO(String senderUsername, @NonNull String recipientUsername, @NonNull String content, Date timestamp, MessageStatus status) {
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
    }
}
