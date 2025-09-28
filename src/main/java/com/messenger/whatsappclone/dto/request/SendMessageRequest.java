package com.messenger.whatsappclone.dto.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long chatId;
    private Long senderId;
    private String content;
}
