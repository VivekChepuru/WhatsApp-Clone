package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    Message sendMessage(UUID senderId, UUID chatId, String content);

    Optional<Message> getMessage(UUID messageId);

    List<Message> getMessagesByChat(Chat chat);

    List<Message> getLatestMessages(Chat chat, int limit);

    void deleteMessage(UUID messageId);
}
