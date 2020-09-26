package messaging.controllers;

import messaging.dtos.ChatMessageDTO;
import messaging.models.chat.ChatMessage;
import messaging.services.chat.ChatMessageService;
import messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    //TODO: ChatMessage DTO
    @PostMapping
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageDTO message) throws Exception {
        ChatMessage savedMessage = chatMessageService.save(message);
        URI location = URI.create(String.format("/v1/chat/%s", savedMessage.getId()));
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages (@PathVariable Long senderId,
                                                               @PathVariable Long recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<ChatMessage> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id).get());
    }

}
