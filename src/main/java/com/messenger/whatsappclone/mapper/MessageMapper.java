package com.messenger.whatsappclone.mapper;

import com.messenger.whatsappclone.dto.response.MessageResponse;
import com.messenger.whatsappclone.entity.Message;

public class MessageMapper {
    public static MessageResponse toResponse(Message message) {
        MessageResponse dto = new MessageResponse();
        dto.setMessageId(message.getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());

        dto.setSenderId(message.getSender().getId());
        dto.setSenderUsername(message.getSender().getUsername());
        dto.setChatId(message.getChat().getId());

        return dto;
    }
}
