package com.messenger.whatsappclone.mapper;

import com.messenger.whatsappclone.dto.response.ChatResponse;
import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;

import java.util.UUID;
import java.util.stream.Collectors;

public class ChatMapper {
    public static ChatResponse toResponse(Chat chat) {
        ChatResponse dto = new ChatResponse();
        dto.setId(chat.getId());
        dto.setChatName(chat.getChatName());
        dto.setGroup(chat.isGroup());

        dto.setParticipants(chat.getParticipants().stream()
                .map(ChatMapper::mapParticipant)
                .collect(Collectors.toList()));

        return dto;
    }

    private static ChatResponse.ParticipantDto mapParticipant(User user) {
        ChatResponse.ParticipantDto p = new ChatResponse.ParticipantDto();
        p.setUserId(user.getUserId());
        p.setUsername(user.getUsername());
        p.setPhoneNumber(user.getPhoneNumber());
        return p;
    }
}
