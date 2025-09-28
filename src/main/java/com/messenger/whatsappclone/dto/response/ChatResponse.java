package com.messenger.whatsappclone.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class ChatResponse {
    private UUID id;
    private String chatName;
    private boolean isGroup;
    private List<ParticipantDto> participants;

    @Data
    public static class ParticipantDto {
        private UUID id;
        private String username;
        private String phoneNumber;
    }
}
