package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;

import java.util.List;

public interface ChatService {
    Chat createChat(String chatName, boolean isGroup, List<User> participants);

    Chat getChatById(Long chatId);

    List<Chat> getAllChats();

    void deleteChat(Long chatId);

    Chat addUserToChat(Long chatId, User user);

    Chat removeUserFromChat(Long chatId, User user);
}
