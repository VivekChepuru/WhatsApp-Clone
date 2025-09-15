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

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message sendMessage(Long senderId, Long chatId, String content) {
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
    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("Message not found"));
    }

    @Override
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("Message not found"));
        messageRepository.delete(message);
    }
}
