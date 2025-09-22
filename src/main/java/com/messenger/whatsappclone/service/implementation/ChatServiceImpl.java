package com.messenger.whatsappclone.service.implementation;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        // remove duplicates while preserving intention
        Set<UUID> uniqueIds = new LinkedHashSet<>(participantIds);

        //Fetch users in bulk
        List<User> users = userRepository.findAllById(uniqueIds);

        // if not all users found -> compute missing
        Set<UUID> foundIds = users.stream().map(User::getId).collect(Collectors.toSet());
        Set<UUID> missing = uniqueIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("The following participants IDs were not found: " + missing);
        }

        // Build chat
        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setGroup(isGroup);
        // use a Set<User>
        chat.setParticipants(new HashSet<>(users));

        // save (transactional)
        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> getChat(UUID chatId) {
        return chatRepository.findById(chatId);
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public List<Chat> getChatsByIds(List<UUID> chatIds) {
        return chatRepository.findAllByIdIn(chatIds);
    }

    @Override
    public void deleteChat(UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                        .orElseThrow(()-> new IllegalArgumentException("Chat with ID " + chatId + " not found."));
        chatRepository.delete(chat);
    }

    @Override
    public Chat addUserToChat(UUID chatId, UUID userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new IllegalArgumentException("Chat not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (!chat.getParticipants().add(user)) {
            throw new IllegalArgumentException("User already in chat.");
        }

        return chatRepository.save(chat);
    }

    @Override
    public Chat removeUserFromChat(UUID chatId, UUID userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new IllegalArgumentException("Chat not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found."));

        if (!chat.getParticipants().remove(user)) {
            throw new IllegalArgumentException("User is not part of this chart.");
        }

        return chatRepository.save(chat);
    }
}
