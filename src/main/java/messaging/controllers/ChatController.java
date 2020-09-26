package messaging.controllers;

import messaging.dtos.ChatMessageDTO;
import messaging.models.chat.ChatMessage;
import messaging.models.chat.ChatNotification;
import messaging.services.chat.ChatMessageService;
import messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping("/messages")
public class ChatController {

    @Autowired private ChatMessageService chatMessageService;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public ChatMessage sendMessage(ChatMessageDTO message) throws Exception {
        ChatMessage savedMessage = chatMessageService.save(message);

        messagingTemplate.convertAndSendToUser(
                savedMessage.getRecipientUsername(),"/queue/messages",
                new ChatNotification(
                        savedMessage.getId(),
                        savedMessage.getSenderUsername()));

        return savedMessage;
    }


    @GetMapping("/{senderUsername}/{recipientUsername}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderUsername,
            @PathVariable String recipientUsername) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderUsername, recipientUsername));
    }

    @GetMapping("/{senderUsername}/{recipientUsername}")
    public ResponseEntity<List<ChatMessage>> findChatMessages (@PathVariable String senderUsername,
                                                               @PathVariable String recipientUsername) {
        List<ChatMessage> chatMessages = chatMessageService.findChatMessages(senderUsername, recipientUsername);
        return ResponseEntity
                .ok(chatMessages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> findMessage ( @PathVariable("id") String id) {
        ChatMessage chatMessage = chatMessageService.findById(id).get();
        return ResponseEntity
                .ok(chatMessage);
    }

}
