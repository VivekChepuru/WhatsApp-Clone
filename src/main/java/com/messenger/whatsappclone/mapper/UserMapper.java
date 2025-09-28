package com.messenger.whatsappclone.mapper;

import com.messenger.whatsappclone.dto.response.UserResponse;
import com.messenger.whatsappclone.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setUserStatus(user.getUserStatus().name());
        return dto;
    }

}
