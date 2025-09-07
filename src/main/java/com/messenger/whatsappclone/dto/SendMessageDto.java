package com.messenger.whatsappclone.dto;

import lombok.Data;

@Data
public class SendMessageDto {
    private Long chatId;
    private Long senderId;
    private String content;
}
