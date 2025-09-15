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
        chat.setParticipants(participants);

        return chatRepository.save(chat);
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(()-> new IllegalArgumentException("Chat with ID " + chatId + " not found."));
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public void deleteChat(Long chatId) {
        Chat chat = getChatById(chatId);
        chatRepository.delete(chat);
    }

    @Override
    public Chat addUserToChat(Long chatId, User user) {
        Chat chat = getChatById(chatId);
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (chat.getParticipants().contains(existingUser)) {
            throw new IllegalArgumentException("User already in chat.");
        }

        chat.getParticipants().add(existingUser);
        return chatRepository.save(chat);
    }

    @Override
    public Chat removeUserFromChat(Long chatId, User user) {
        Chat chat = getChatById(chatId);
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (!chat.getParticipants().contains(existingUser)) {
            throw new IllegalArgumentException("User is not part of this chart.");
        }

        chat.getParticipants().remove(existingUser);
        return chatRepository.save(chat);
    }
}
