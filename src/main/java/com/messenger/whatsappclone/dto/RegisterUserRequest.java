package com.messenger.whatsappclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private String username;
    private String phoneNumber;
    private String password;
}
