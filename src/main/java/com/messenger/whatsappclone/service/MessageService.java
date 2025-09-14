package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long chatId, String content);

    List<Message> getMessageByChatId(Long chatId);

    Message getMessageById(Long messageId);

    void deleteMessage(Long messageId);
}
