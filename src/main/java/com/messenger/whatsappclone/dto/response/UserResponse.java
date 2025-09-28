package com.messenger.whatsappclone.dto.response;

import lombok.Data;

import java.util.UUID;
@Data
public class UserResponse {
    private UUID id;
    private String userId;
    private String username;
    private String phoneNumber;
    private String userStatus;
}
