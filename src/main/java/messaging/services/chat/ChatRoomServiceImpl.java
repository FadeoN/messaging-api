package messaging.services.chat;

import messaging.models.chat.ChatRoom;
import messaging.repository.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
            String senderUsername, String recipientUsername, boolean createIfNotExist) {

        return chatRoomRepository
                .findBySenderUsernameAndRecipientUsername(senderUsername, recipientUsername)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    String chatId =
                            String.format("%s_%s", senderUsername, recipientUsername);

                    ChatRoom senderRecipient = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderUsername(senderUsername)
                            .recipientUsername(recipientUsername)
                            .build();

                    ChatRoom recipientSender = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderUsername(recipientUsername)
                            .recipientUsername(senderUsername)
                            .build();
                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}