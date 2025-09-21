package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    Chat createChat(String chatName, boolean isGroup, List<UUID> participants);

    Optional<Chat> getChatByChatId(UUID chatId);

    List<Chat> getAllChats();

    List<Chat> getChatsByIds(List<UUID> chatIds);

    void deleteChat(UUID chatId);

    Chat addUserToChat(UUID chatId, UUID userId);

    Chat removeUserFromChat(UUID chatId, UUID userId);
}
