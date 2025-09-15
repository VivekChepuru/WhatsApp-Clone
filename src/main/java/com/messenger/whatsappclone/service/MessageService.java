package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long chatId, String content);

    List<Message> getMessagesByChatId(Long chatId);

    Message getMessageById(Long messageId);

    void deleteMessage(Long messageId);
}
