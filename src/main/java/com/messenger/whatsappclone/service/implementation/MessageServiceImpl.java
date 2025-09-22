package com.messenger.whatsappclone.service.implementation;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.MessageRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message sendMessage(UUID senderId, UUID chatId, String content) {
        if (content == null || content.trim().isEmpty()){
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new IllegalArgumentException("Sender not found"));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new IllegalArgumentException("Chat not found"));

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setChat(chat);

        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> getMessage(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    @Override
    public List<Message> getMessagesByChat(Chat chat) {
        return messageRepository.findByChatOrderByTimestampAsc(chat);
    }

    @Override
    public List<Message> getLatestMessages(Chat chat, int limit) {
        List<Message> recent = messageRepository.findTop50ByChatOrderByTimestampDesc(chat);
        return recent.stream().limit(limit).toList();
    }

    @Override
    public void deleteMessage(UUID messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new IllegalArgumentException("Message not found.");
        }
        messageRepository.deleteById(messageId);
    }
}
