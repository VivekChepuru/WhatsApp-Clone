package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.MessageRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public Message sendMessage(Long chatId, Long senderId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        Message msg = new Message();
        msg.setContent(content);
        msg.setSender(sender);
        msg.setChat(chat);
        msg.setTimestamp(LocalDateTime.now());

        return messageRepository.save(msg);
    }
}
