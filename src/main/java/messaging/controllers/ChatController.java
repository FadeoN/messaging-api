package messaging.controllers;

import messaging.dtos.ChatMessageDTO;
import messaging.models.chat.ChatMessage;
import messaging.services.chat.ChatMessageService;
import messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping("/v1/chat")
public class ChatController {

    @Autowired private ChatMessageService chatMessageService;

    @MessageMapping("/hello")
    @SendTo("topic/greetings")
    public ResponseEntity<ChatMessage> sendMessage(ChatMessageDTO message) throws Exception {
        ChatMessage savedMessage = chatMessageService.save(message);
        URI location = URI.create(String.format("/v1/chat/%s", savedMessage.getId()));
        return ResponseEntity.created(location).build();
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

    @GetMapping("/messages/{id}")
    public ResponseEntity<ChatMessage> findMessage ( @PathVariable("id") String id) {
        ChatMessage chatMessage = chatMessageService.findById(id).get();
        return ResponseEntity
                .ok(chatMessage);
    }

}
