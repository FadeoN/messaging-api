package com.messaging.controllers;

import com.messaging.dtos.ChatMessageDTO;
import com.messaging.models.chat.ChatMessage;
import com.messaging.models.chat.ChatNotification;
import com.messaging.services.chat.ChatMessageService;
import com.messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping(URLConstants.CHAT_BASE_URL)
public class ChatController {

    @Autowired private ChatMessageService chatMessageService;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public ChatMessage sendMessage(@AuthenticationPrincipal Principal user, ChatMessageDTO message) throws Exception {

        /*Get Current User*/

        String senderUsername = user.getName();
        message.setSenderUsername(senderUsername);

        ChatMessage savedMessage = chatMessageService.save(message);

        messagingTemplate.convertAndSendToUser(
                savedMessage.getRecipientUsername(),"/queue/messages",
                new ChatNotification(
                        savedMessage.getId(),
                        savedMessage.getSenderUsername()));

        return savedMessage;
    }


    @GetMapping(URLConstants.CHAT_COUNT_URL)
    public ResponseEntity<Long> countNewMessages(@AuthenticationPrincipal Principal user,
            @PathVariable("id") String recipientUsername) {

        /*Get Current User*/
        String senderUsername = user.getName();


        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderUsername, recipientUsername));
    }

    @GetMapping(URLConstants.CHAT_MESSAGE_LIST_URL)
    public ResponseEntity<List<ChatMessage>> findChatMessages (@AuthenticationPrincipal Principal user, @PathVariable("id") String senderUsername) {
        /*Get Current User*/
        String loggedInUsername = user.getName();


        List<ChatMessage> chatMessages = chatMessageService.findChatMessages(loggedInUsername, senderUsername);
        return ResponseEntity
                .ok(chatMessages);
    }

    @GetMapping(URLConstants.CHAT_MESSAGE_URL)
    public ResponseEntity<ChatMessage> findMessage (@PathVariable("id") String id) {
        ChatMessage chatMessage = chatMessageService.findById(id).get();
        return ResponseEntity
                .ok(chatMessage);
    }

}
