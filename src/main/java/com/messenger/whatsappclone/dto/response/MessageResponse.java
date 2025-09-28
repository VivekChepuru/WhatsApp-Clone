package com.messenger.whatsappclone.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponse {
    private UUID messageId;      // maps directly from entity.getId()
    private String content;
    private LocalDateTime timestamp;

    private UUID senderId;
    private String senderUsername;
    private UUID chatId;
}
