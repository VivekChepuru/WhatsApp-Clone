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
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Chat createChat(String chatName, boolean isGroup, List<UUID> participantIds) {
        if (isGroup && (chatName == null || chatName.trim().isEmpty())) {
            throw new IllegalArgumentException("Group chat must have a name.");
        }

        if (participantIds == null || participantIds.isEmpty()) {
            throw new IllegalArgumentException("Chat must have at least one participant. ");
        }

        // Fetch all users by their IDs
        List<User> participants = userRepository.findAllById(participantIds);
        
        if (participants.size() != participantIds.size()) {
            throw new IllegalArgumentException("One or more participants not found in the system.");
        }

        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setGroup(isGroup);
        chat.setParticipants(participantIds);

        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> getChatByChatId(UUID chatId) {
        return Optional.ofNullable(chatRepository.findByChatId(chatId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Chat with ID " + chatId + " not found.")));
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public List<Chat> getChatsByIds(List<UUID> chatIds) {
        return chatRepository.findAllByChatIdIn(chatIds.stream().map(UUID::toString).toList());
    }

    @Override
    public void deleteChat(UUID chatId) {
        Chat chat = chatRepository.findByChatId(chatId.toString())
                        .orElseThrow(()-> new IllegalArgumentException("Chat with ID " + chatId + " not found."));
        chatRepository.delete(chat);
    }

    @Override
    public Chat addUserToChat(UUID chatId, UUID userId) {
        Chat chat = chatRepository.findByChatId(chatId.toString())
                .orElseThrow(()-> new IllegalArgumentException("Chat not found."));
        User user = userRepository.findByUserId(userId.toString())
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (chat.getParticipants().contains(user)) {
            throw new IllegalArgumentException("User already in chat.");
        }

        chat.getParticipants().add(user);
        return chatRepository.save(chat);
    }

    @Override
    public Chat removeUserFromChat(UUID chatId, UUID userId) {
        Chat chat = chatRepository.findByChatId(chatId.toString())
                .orElseThrow(()-> new IllegalArgumentException("Chat not found."));
        User user = userRepository.findByUserId(userId.toString())
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (!chat.getParticipants().contains(user)) {
            throw new IllegalArgumentException("User is not part of this chart.");
        }

        chat.getParticipants().remove(user);
        return chatRepository.save(chat);
    }
}
