package com.messenger.whatsappclone.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponse {
    private UUID id;             // Message ID
    private UUID chatId;
    private String content;
    private LocalDateTime timestamp;

    // Sender info
    private UUID senderId;
    private String senderUsername;
    private String senderPhoneNumber;  // Optional: might be useful

    // Optional: for future features
    private UUID replyToMessageId;
    private boolean isEdited;
    private boolean isDeleted;
}
