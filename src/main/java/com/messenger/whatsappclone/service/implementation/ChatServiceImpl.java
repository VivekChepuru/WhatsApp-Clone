package com.messenger.whatsappclone.service.implementation;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.MessageRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(String chatName, boolean isGroup, List<User> participants) {
        if (isGroup && (chatName == null || chatName.trim().isEmpty())) {
            throw new IllegalArgumentException("Group chat must have a name.");
        }

        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Chat must have at least one participant. ");
        }

        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setGroup(isGroup);
        chat.setParticipant(participants);

        return chatRepository.save(chat);
    }

    @Override
    public Chat getChatById(Long chatId) {
        return null;
    }

    @Override
    public List<Chat> getAllChats() {
        return List.of();
    }

    @Override
    public void deleteChat(Long chatId) {

    }

    @Override
    public Chat addUserToChat(Long chatId, User user) {
        return null;
    }

    @Override
    public Chat removeUserFromChat(Long chatId, User user) {
        return null;
    }
}
